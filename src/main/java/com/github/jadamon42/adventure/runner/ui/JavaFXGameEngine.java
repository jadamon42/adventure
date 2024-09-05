package com.github.jadamon42.adventure.runner.ui;

import com.github.jadamon42.adventure.common.model.*;
import com.github.jadamon42.adventure.common.node.*;
import com.github.jadamon42.adventure.common.state.*;
import com.github.jadamon42.adventure.runner.GameEngine;
import com.github.jadamon42.adventure.common.state.GameStateManager;
import com.github.jadamon42.adventure.common.util.PlayerDeltaBiFunction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class JavaFXGameEngine implements GameEngine, StoryNodeVisitor {
    private Player player;
    private StoryNode currentNode;
    private final GameStateManager gameStateManager;
    private CheckpointDelta.Builder checkpointDeltaBuilder;
    private GameState gameState;
    private UiController uiController;

    public JavaFXGameEngine(Player player, StoryNode startNode) {
        this.player = player;
        this.currentNode = startNode;
        this.gameStateManager = new GameStateManager();
        Checkpoint checkpoint = new Checkpoint(player, startNode);
        this.checkpointDeltaBuilder = CheckpointDelta.newBuilder();
        this.gameState = new GameState(checkpoint);
    }

    public void initialize(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/runnerui.fxml"));
        Parent root = loader.load();
        uiController = loader.getController();

        stage.setScene(new Scene(root));
        stage.setTitle("Choose Your Own Adventure");
        stage.show();
    }

    @Override
    public void startGame() {
        new Thread(() -> {
            while (currentNode != null) {
                currentNode.accept(this);
            }
            askToPlayAgain();
        }).start();
    }

    private void askToPlayAgain() {
        uiController.addChoiceButton("Play Again", e -> restartGame());
        uiController.addChoiceButton("Exit Game", e -> uiController.exit());
        uiController.showButtonInput();
    }

    @Override
    public void loadGame(String saveFile) {
        try {
            gameState = gameStateManager.loadGame(saveFile);
            loadGame(gameState.getLatestCheckpoint());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void saveGame(String saveFile) {
        try {
            gameStateManager.saveGame(saveFile, gameState);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void loadGame(UUID messageId) {
        gameState.resetToCheckpoint(messageId);
        checkpointDeltaBuilder = new CheckpointDelta.Builder();
        loadGame(gameState.getCheckpointForMessageId(messageId));
    }

    private void restartGame() {
        Checkpoint checkpoint = gameState.getInitialCheckpoint();
        this.player = checkpoint.getPlayer();
        this.currentNode = checkpoint.getCurrentNode();
        this.gameState.reset();
        checkpointDeltaBuilder = new CheckpointDelta.Builder();
        uiController.clearMessages();
        startGame();
    }

    private void loadGame(Checkpoint checkpoint) {
        if (checkpoint != null) {
            this.player = checkpoint.getPlayer();
            this.currentNode = checkpoint.getCurrentNode();
            replayMessages(checkpoint.getMessageHistory());
            for (TextMessage textMessage : checkpoint.getMessageHistory()) {
                if (textMessage.isInteractable()) {
                    uiController.showReplayButtonFor(textMessage.getId());
                }
            }
            startGame();
        }
    }

    private void replayMessages(MessageHistory messageHistory) {
        uiController.clearMessages();
        for (TextMessage message : messageHistory) {
            if (message.isPlayerMessage() || !message.isInteractable()) {
                uiController.addImmediateMessage(message, player);
            } else {
                uiController.addImmediateMessage(message, player, e -> loadGame(message.getId()));
            }
        }
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void visit(ExpositionalTextNode node) {
        addGameMessage(node.getMessage());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireItemTextNode node) {
        addGameMessage(node.getMessage());
        PlayerDelta playerDelta = player.addItem(node.getItem());
        checkpointDeltaBuilder.applyPlayerDelta(playerDelta);
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireEffectTextNode node) {
        addGameMessage(node.getMessage());
        PlayerDelta playerDelta = player.addEffect(node.getEffect());
        checkpointDeltaBuilder.applyPlayerDelta(playerDelta);
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(FreeTextInputNode node) {
        UUID messageId = addInteractableGameMessage(node.getMessage());

        CountDownLatch latch = new CountDownLatch(1);
        uiController.showTextInput(e -> {
            String input = uiController.getTextInput();
            PlayerDeltaBiFunction<Object> textConsumer = node.getTextConsumer();
            if (textConsumer != null) {
                PlayerDelta playerDelta = textConsumer.apply(player, input);
                checkpointDeltaBuilder.applyPlayerDelta(playerDelta);
            }
            addPlayerMessage(new TextMessage(input, true));
            currentNode = node.getNextNode();
            uiController.hideTextInput();
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            uiController.exit();
        }

        uiController.showReplayButtonFor(messageId);
    }

    @Override
    public void visit(ChoiceTextInputNode node) {
        UUID messageId = addInteractableGameMessage(node.getMessage());

        CountDownLatch latch = new CountDownLatch(1);
        for (LinkedConditionalText choice : node.getChoices(player)) {
            uiController.addChoiceButton(choice.getText(), e -> {
                addPlayerMessage(choice.getMessage());
                currentNode = choice.getNextNode();
                uiController.hideButtonInput();
                latch.countDown();
            });
        }
        uiController.showButtonInput();

        try {
            latch.await();
        } catch (InterruptedException e) {
            uiController.exit();
        }

        uiController.showReplayButtonFor(messageId);
    }

    @Override
    public void visit(BranchNode node) {
        LinkedConditionalText availableChoice = node.getBranch(player);
        addGameMessage(availableChoice.getMessage());
        currentNode = availableChoice.getNextNode();
    }

    @Override
    public void visit(SwitchNode node) {
        ConditionalText availableChoice = node.getCase(player);
        addGameMessage(availableChoice.getMessage());
        currentNode = node.getNextNode();
    }

    private UUID addGameMessage(TextMessage message) {
        uiController.addMessageWithTypingIndicator(message, player);
        checkpointDeltaBuilder.addMessage(message);
        return message.getId();
    }

    private UUID addInteractableGameMessage(TextMessage message) {
        checkpointDeltaBuilder.setCurrentMessageId(message.getId());
        checkpointDeltaBuilder.setCurrentNodeId(currentNode.getId());
        gameState.addCheckpoint(checkpointDeltaBuilder.build());
//        saveGame("sampleRunnerSave.adv");
        checkpointDeltaBuilder = CheckpointDelta.newBuilder();
        checkpointDeltaBuilder.addMessage(message);

        uiController.addMessageWithTypingIndicator(message, player, e -> loadGame(message.getId()));
        return message.getId();
    }

    private void addPlayerMessage(TextMessage message) {
        uiController.addImmediateMessage(message, player);
        checkpointDeltaBuilder.addMessage(message);
    }
}
