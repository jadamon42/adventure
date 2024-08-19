package com.github.jadamon42.adventure.engine.ui;

import com.github.jadamon42.adventure.engine.GameEngine;
import com.github.jadamon42.adventure.engine.GameStateManager;
import com.github.jadamon42.adventure.model.*;
import com.github.jadamon42.adventure.node.*;
import com.github.jadamon42.adventure.util.SerializableBiFunction;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class JavaFXGameEngine implements GameEngine, StoryNodeVisitor {
    private Player player;
    private StoryNode currentNode;
    private final GameStateManager gameStateManager;
    private CheckpointDelta.Builder checkpointDeltaBuilder;
    private GameState gameState;
    private VBox messagePanel;
    private TextField inputField;
    private ScrollPane scrollPane;

    public JavaFXGameEngine(Player player, StoryNode startNode) {
        this.player = player;
        this.currentNode = startNode;
        this.gameStateManager = new GameStateManager();
        Checkpoint checkpoint = new Checkpoint(player, startNode);
        this.checkpointDeltaBuilder = CheckpointDelta.newBuilder();
        this.gameState = new GameState(checkpoint);
    }

    public void initialize(Stage stage) {
        stage.setTitle("Choose Your Own Adventure");

        messagePanel = new VBox();
        scrollPane = new ScrollPane(messagePanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        inputField = new TextField();
        inputField.setDisable(true);

        VBox root = new VBox();
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        root.getChildren().addAll(scrollPane, inputField);
        Scene scene = new Scene(root, 600, 400);

        stage.setScene(scene);
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
        Platform.runLater(() -> {
            FlowPane buttonPane = new FlowPane();
            buttonPane.setAlignment(Pos.CENTER_RIGHT);
            buttonPane.setHgap(10);
            buttonPane.setVgap(10);
            buttonPane.setPrefWrapLength(600);

            Button againButton = new Button("Play Again");
            againButton.setOnAction(e -> restartGame());
            againButton.setFocusTraversable(false);
            buttonPane.getChildren().add(againButton);

            Button exitButton = new Button("Exit Game");
            exitButton.setOnAction(e -> Platform.exit());
            exitButton.setFocusTraversable(false);
            buttonPane.getChildren().add(exitButton);

            messagePanel.getChildren().add(buttonPane);
            scrollToBottom();
        });
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
        Platform.runLater(() -> {
            try {
                gameStateManager.saveGame(saveFile, gameState);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        });
    }

    private void loadGame(UUID messageId) {
        loadGame(gameState.getCheckpointForMessageId(messageId));
        gameState.resetToCheckpoint(messageId);
        checkpointDeltaBuilder = new CheckpointDelta.Builder();
    }

    private void restartGame() {
        Checkpoint checkpoint = gameState.getInitialCheckpoint();
        this.player = checkpoint.getPlayer();
        this.currentNode = checkpoint.getCurrentNode();
        this.gameState.reset();
        clearMessages();
        startGame();
    }

    private void loadGame(Checkpoint checkpoint) {
        if (checkpoint != null) {
            this.player = checkpoint.getPlayer();
            this.currentNode = checkpoint.getCurrentNode();
            replayMessages(checkpoint.getMessageHistory());
            for (TextMessage textMessage : checkpoint.getMessageHistory()) {
                if (textMessage.isInteractable()) {
                    makeReplayButtonVisibleFor(textMessage.getId());
                }
            }
            startGame();
        }
    }

    private void clearMessages() {
        Platform.runLater(() -> messagePanel.getChildren().clear());
    }

    private void replayMessages(MessageHistory messageHistory) {
        clearMessages();
        for (TextMessage textMessage : messageHistory) {
            addMessageToPanel(textMessage);
        }
    }

    private void makeReplayButtonVisibleFor(UUID messageId) {
        Platform.runLater(() -> {
            HBox hbox = (HBox) messagePanel.lookup("#" + messageId.toString());
            if (hbox != null) {
                for (Node child : hbox.getChildren()) {
                    if (child instanceof Button && "Replay".equals(((Button) child).getText())) {
                        child.setVisible(true);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void visit(LinkableStoryTextNode node) {
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
        Platform.runLater(() -> {
            inputField.setDisable(false);
            inputField.requestFocus();
            inputField.setOnAction(e -> {
                String input = inputField.getText();
                inputField.setText("");
                inputField.setDisable(true);
                SerializableBiFunction<Player, Object, PlayerDelta> textConsumer = node.getTextConsumer();
                if (textConsumer != null) {
                    PlayerDelta playerDelta = textConsumer.apply(player, input);
                    checkpointDeltaBuilder.applyPlayerDelta(playerDelta);
                }
                addPlayerMessage(new TextMessage(input, true));
                currentNode = node.getNextNode();
                latch.countDown();
            });
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Platform.runLater(() -> Thread.currentThread().interrupt());
        }

        makeReplayButtonVisibleFor(messageId);
    }

    @Override
    public void visit(ChoiceTextInputNode node) {
        UUID messageId = addInteractableGameMessage(node.getMessage());

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            FlowPane buttonPane = new FlowPane();
            buttonPane.setAlignment(Pos.CENTER_RIGHT);
            buttonPane.setHgap(10);
            buttonPane.setVgap(10);
            buttonPane.setPrefWrapLength(600);

            List<Button> buttons = new ArrayList<>();
            for (LinkedConditionalText choice : node.getChoices(player)) {
                Button button = new Button(choice.getText());
                button.setOnAction(e -> {
                    currentNode = choice.getNextNode();
//                    for (Button b : buttons) {
//                        b.setDisable(true);
//                    }
//                    button.setStyle("-fx-background-color: grey;");
                    messagePanel.getChildren().remove(buttonPane);
                    addPlayerMessage(choice.getMessage());
                    latch.countDown();
                });
                button.setFocusTraversable(false);
                buttons.add(button);
                buttonPane.getChildren().add(button);
            }

            messagePanel.getChildren().add(buttonPane);
            scrollToBottom();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Platform.runLater(() -> Thread.currentThread().interrupt());
        }

        makeReplayButtonVisibleFor(messageId);
    }

    @Override
    public void visit(BranchNode node) {
        LinkedConditionalText availableChoice = node.getChoice(player);
        addGameMessage(availableChoice.getMessage());
        currentNode = availableChoice.getNextNode();
    }

    @Override
    public void visit(SwitchNode node) {
        ConditionalText availableChoice = node.getChoice(player);
        addGameMessage(availableChoice.getMessage());
        currentNode = node.getNextNode();
    }

    private void addMessageToPanel(TextMessage textMessage) {
        Platform.runLater(() -> {
            Label label = new Label(textMessage.getInterpolatedText(player));
            label.setWrapText(true);

            HBox hbox = new HBox();
            hbox.setId(textMessage.getId().toString());
            hbox.setAlignment(textMessage.isPlayerMessage() ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

            if (textMessage.isInteractable()) {
                Button replayButton = new Button("Replay");
                replayButton.setFocusTraversable(false);
                replayButton.setVisible(false);
                replayButton.setOnAction(e -> loadGame(textMessage.getId()));
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                hbox.getChildren().addAll(label, spacer, replayButton);
            } else {
                hbox.getChildren().add(label);
            }

            messagePanel.getChildren().add(hbox);
            scrollToBottom();
        });
    }

    private UUID addGameMessage(TextMessage textMessage) {
        addMessageToPanel(textMessage);
        checkpointDeltaBuilder.addMessage(textMessage);
        return textMessage.getId();
    }

    private UUID addInteractableGameMessage(TextMessage textMessage) {
        checkpointDeltaBuilder.setCurrentMessageId(textMessage.getId());
        checkpointDeltaBuilder.setCurrentNodeId(currentNode.getId());
        gameState.addCheckpoint(checkpointDeltaBuilder.build());
        checkpointDeltaBuilder = CheckpointDelta.newBuilder();
        checkpointDeltaBuilder.addMessage(textMessage);

        addMessageToPanel(textMessage);
        return textMessage.getId();
    }

    private void addPlayerMessage(TextMessage textMessage) {
        addMessageToPanel(textMessage);
        checkpointDeltaBuilder.addMessage(textMessage);
    }

    private void scrollToBottom() {
        scrollPane.applyCss();
        scrollPane.layout();
        scrollPane.setVvalue(scrollPane.getVmax());
    }
}
