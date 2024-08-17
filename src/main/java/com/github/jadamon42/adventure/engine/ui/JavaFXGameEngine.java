package com.github.jadamon42.adventure.engine.ui;

import com.github.jadamon42.adventure.engine.GameEngine;
import com.github.jadamon42.adventure.engine.GameStateManager;
import com.github.jadamon42.adventure.model.*;
import com.github.jadamon42.adventure.node.*;
import com.github.jadamon42.adventure.util.TextInterpolator;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;

public class JavaFXGameEngine implements GameEngine, StoryNodeVisitor {
    private Player player;
    private StoryNode currentNode;
    private final GameStateManager gameStateManager;
    private CheckpointDelta.Builder checkpointDeltaBuilder;
    private GameState gameState;
    private boolean loadedGame;
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
        this.loadedGame = false;

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
            scrollPane.setVvalue(1.0);
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

    private void loadGame(UUID checkpointId) {
        loadGame(gameState.getCheckpointForMessageId(checkpointId));
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
            this.loadedGame = true;
            this.player = checkpoint.getPlayer();
            this.currentNode = checkpoint.getCurrentNode();
            replayMessages(checkpoint.getMessageHistory());
            startGame();
        }
    }

    private void clearMessages() {
        Platform.runLater(() -> {
            messagePanel.getChildren().clear();
        });
    }

    private void replayMessages(MessageHistory messageHistory) {
        clearMessages();
        for (Message message : messageHistory) {
            addMessageToPanel(message);
        }
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void visit(LinkableStoryTextNode node) {
        addGameMessage(node.getText());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireItemTextNode node) {
        addGameMessage(node.getText());
        PlayerDelta playerDelta = player.addItem(node.getItem());
        checkpointDeltaBuilder.applyPlayerDelta(playerDelta);
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireEffectTextNode node) {
        addGameMessage(node.getText());
        PlayerDelta playerDelta = player.addEffect(node.getEffect());
        checkpointDeltaBuilder.applyPlayerDelta(playerDelta);
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(FreeTextInputNode node) {
        addInteractableGameMessage(node.getText());

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            inputField.setDisable(false);
            inputField.requestFocus();
            inputField.setOnAction(e -> {
                String input = inputField.getText();
                inputField.setText("");
                inputField.setDisable(true);
                BiConsumer<Player, Object> textConsumer = node.getTextConsumer();
                if (textConsumer != null) {
                    textConsumer.accept(player, input);
                }
                addPlayerMessage(input);
                currentNode = node.getNextNode();
                latch.countDown();
            });
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Platform.runLater(() -> Thread.currentThread().interrupt());
        }
    }

    @Override
    public void visit(ChoiceTextInputNode node) {
        addInteractableGameMessage(node.getText());

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            FlowPane buttonPane = new FlowPane();
            buttonPane.setAlignment(Pos.CENTER_RIGHT);
            buttonPane.setHgap(10);
            buttonPane.setVgap(10);
            buttonPane.setPrefWrapLength(600);

            List<Button> buttons = new ArrayList<>();
            for (LinkedTextChoice choice : node.getChoices(player)) {
                Button button = new Button(choice.getText());
                button.setOnAction(e -> {
                    currentNode = choice.getNextNode();
//                    for (Button b : buttons) {
//                        b.setDisable(true);
//                    }
//                    button.setStyle("-fx-background-color: grey;");
                    messagePanel.getChildren().remove(buttonPane);
                    addPlayerMessage(choice.getText());
                    latch.countDown();
                });
                button.setFocusTraversable(false);
                buttons.add(button);
                buttonPane.getChildren().add(button);
            }

            messagePanel.getChildren().add(buttonPane);
            scrollPane.setVvalue(1.0);
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Platform.runLater(() -> Thread.currentThread().interrupt());
        }
    }

    @Override
    public void visit(BranchNode node) {
        LinkedTextChoice availableChoice = node.getChoice(player);
        addGameMessage(availableChoice.getText());
        currentNode = availableChoice.getNextNode();
    }

    @Override
    public void visit(SwitchNode node) {
        TextChoice availableChoice = node.getChoice(player);
        addGameMessage(availableChoice.getText());
        currentNode = node.getNextNode();
    }

    private void addMessageToPanel(Message message) {
        Platform.runLater(() -> {
            Label label = new Label(message.getText());
            label.setWrapText(true);

            HBox hbox = new HBox(label);
            hbox.setAlignment(message.isPlayerMessage() ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

            messagePanel.getChildren().add(hbox);
            scrollPane.setVvalue(1.0);
        });
    }

    private UUID addGameMessage(String text) {
        String interpolated = TextInterpolator.interpolate(text, player);
        Message message = new Message(interpolated, false);

        addMessageToPanel(message);
        checkpointDeltaBuilder.addMessage(message);
        return message.getId();
    }

    private void addInteractableGameMessage(String text) {
        if (!loadedGame) {
            UUID messageId = addGameMessage(text);
            checkpointDeltaBuilder.setCurrentMessageId(messageId);
            checkpointDeltaBuilder.setCurrentNodeId(currentNode.getId());
            gameState.addCheckpoint(checkpointDeltaBuilder.build());
            checkpointDeltaBuilder = CheckpointDelta.newBuilder();
        } else {
            loadedGame = false;
        }
    }

    private void addPlayerMessage(String text) {
        Message message = new Message(text, true);

        addMessageToPanel(message);
        checkpointDeltaBuilder.addMessage(message);
    }
}
