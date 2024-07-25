package com.github.jadamon42.adventure.ui;

import com.github.jadamon42.adventure.*;
import com.github.jadamon42.adventure.model.*;
import com.github.jadamon42.adventure.node.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
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

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class JavaFXGameEngine implements GameEngine, StoryNodeVisitor {
    private static final UUID NIL_UUID = new UUID(0, 0);
    private Player player;
    private StoryNode currentNode;
    private final GameStateManager gameStateManager;
    private List<Message> messageHistory;
    private Map<UUID, GameState> interactableCheckpoints;
    private VBox messagePanel;
    private TextField inputField;
    private ScrollPane scrollPane;

    public JavaFXGameEngine(Player player, StoryNode startNode) {
        this.player = player;
        this.currentNode = startNode;
        this.gameStateManager = new GameStateManager();
        this.messageHistory = new ArrayList<>();
        this.interactableCheckpoints = new HashMap<>();
        interactableCheckpoints.put(
                NIL_UUID,
                new GameState(
                        new Player(player),
                        currentNode,
                        new ArrayList<>(),
                        new HashMap<>()));
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

            Button button = new Button("Play Again");
            button.setOnAction(e -> {
                loadGame(NIL_UUID);
            });
            button.setFocusTraversable(false);
            buttonPane.getChildren().add(button);

            messagePanel.getChildren().add(buttonPane);
            scrollPane.setVvalue(1.0);
        });
    }

    @Override
    public void loadGame(String saveFile) {

    }

    @Override
    public void saveGame(String saveFile) {

    }

    private void loadGame(UUID checkpointId) {
        GameState gameState = interactableCheckpoints.get(checkpointId);
        if (gameState != null) {
            this.player.setName(gameState.getPlayer().getName());
            this.player.setInventory(new Inventory(gameState.getPlayer().getInventory()));
            this.player.setEffects(new Effects(gameState.getPlayer().getEffects()));
            this.currentNode = gameState.getCurrentNode();
            this.messageHistory = new ArrayList<>(gameState.getMessageHistory());
            if (interactableCheckpoints == null) {
                this.interactableCheckpoints = gameState.getMessageToGameStateMap();
            }
            replayMessages();
        }
    }

    private void replayMessages() {
        Platform.runLater(() -> {
            messagePanel.getChildren().clear();
            for (Message message : messageHistory) {
                if (message.isPlayerMessage()) {
                    addPlayerMessage(message.getText());
                } else {
                    addGameMessage(message.getText());
                }
            }
            scrollPane.setVvalue(1.0);
            startGame();
        });
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void visit(LinkableTextNode node) {
        addGameMessage(node.getText());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireItemTextNode node) {
        addGameMessage(node.getText());
        player.addItem(node.getItem());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireEffectTextNode node) {
        addGameMessage(node.getText());
        player.addEffect(node.getEffect());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(FreeTextInputNode node) {
        addGameMessage(node.getText());

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            inputField.setDisable(false);
            inputField.requestFocus();
            inputField.setOnAction(e -> {
                String input = inputField.getText();
                inputField.setText("");
                inputField.setDisable(true);
                Consumer<String> textConsumer = node.getTextConsumer();
                if (textConsumer != null) {
                    textConsumer.accept(input);
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
        addGameMessage(node.getText());

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            FlowPane buttonPane = new FlowPane();
            buttonPane.setAlignment(Pos.CENTER_RIGHT);
            buttonPane.setHgap(10);
            buttonPane.setVgap(10);
            buttonPane.setPrefWrapLength(600);

            List<Button> buttons = new ArrayList<>();
            for (TextChoice choice : node.getChoices(player)) {
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
    public void visit(ConditionalTextNode node) {
        TextChoice availableChoice = node.getChoice(player);
        addGameMessage(availableChoice.getText());
        currentNode = availableChoice.getNextNode();
    }

    private void addGameMessage(String text) {
        String interpolated = TextInterpolator.interpolate(text, player);
        Message message = new Message(interpolated, false);

        Platform.runLater(() -> {
            Label label = new Label(message.getText());
            label.setWrapText(true);

            HBox hbox = new HBox(label);
            hbox.setAlignment(Pos.CENTER_LEFT);

            messagePanel.getChildren().add(hbox);
            scrollPane.setVvalue(1.0);
        });

        messageHistory.add(message);
    }

    private void handleInteractableGameMessage(String text) {
        addGameMessage(text);
    }

    private void addPlayerMessage(String text) {
        Message message = new Message(text, true);

        Platform.runLater(() -> {
            Label label = new Label(message.getText());
            label.setWrapText(true);

            HBox hbox = new HBox(label);
            hbox.setAlignment(Pos.CENTER_RIGHT);

            messagePanel.getChildren().add(hbox);
            scrollPane.setVvalue(1.0);
        });
        messageHistory.add(message);
    }
}
