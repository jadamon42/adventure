package com.github.jadamon42.adventure.runner.ui;

import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.model.TextMessage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import com.jfoenix.controls.JFXButton;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class UiController {
    @FXML
    private ScrollPane messagesScrollPane;

    @FXML
    private VBox messagesContainer;

    @FXML
    private HBox textInputContainer;

    @FXML
    private TextField textInput;

    @FXML
    private FlowPane buttonInputContainer;

    private final Queue<MessageContainer> messageQueue;

    public UiController() {
        this.messageQueue = new LinkedList<>();
    }

    @FXML
    public void initialize() {
        hideTextInput();
        hideButtonInput();
    }

    public void exit() {
        Platform.exit();
    }

    public void hideTextInput() {
        Platform.runLater(() -> {
            textInput.clear();
            textInputContainer.setVisible(false);
            textInputContainer.setManaged(false);
            scrollToBottom();
        });
    }

    public void showTextInput(EventHandler<ActionEvent> eventHandler) {
        CountDownLatch latch = new CountDownLatch(2);
        List<String> messageContainerIds = hideAllReplayButtons(latch);
        processMessageQueue(latch);
        new Thread(() -> {
            try {
                latch.await();
                showReplayButtonFor(messageContainerIds);
                Platform.runLater(() -> {
                    hideButtonInput();
                    textInputContainer.setVisible(true);
                    textInputContainer.setManaged(true);
                    textInput.setOnAction(eventHandler);
                    textInput.requestFocus();
                    scrollToBottom();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                exit();
            }
        }).start();
    }

    public String getTextInput() {
        return textInput.getText();
    }

    public void hideButtonInput() {
        Platform.runLater(() -> {
            buttonInputContainer.getChildren().clear();
            buttonInputContainer.setVisible(false);
            buttonInputContainer.setManaged(false);
            scrollToBottom();
        });
    }

    public void showButtonInput() {
        CountDownLatch latch = new CountDownLatch(2);
        List<String> messageContainerIds = hideAllReplayButtons(latch);
        processMessageQueue(latch);
        new Thread(() -> {
            try {
                latch.await();
                showReplayButtonFor(messageContainerIds);
                Platform.runLater(() -> {
                    hideTextInput();
                    buttonInputContainer.setVisible(true);
                    buttonInputContainer.setManaged(true);
                    scrollToBottom();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void addChoiceButton(String text, EventHandler<ActionEvent> eventHandler) {
        Platform.runLater(() -> {
            JFXButton button = new JFXButton(text);
            button.getStyleClass().add("choice-button");
            button.setOnAction(eventHandler);
            buttonInputContainer.getChildren().add(button);
        });
    }

    public void addImmediateMessage(TextMessage message, Player player, EventHandler<MouseEvent> onReplay) {
        Platform.runLater(() -> {
            MessageContainer messageContainer = new MessageContainer(message, player, onReplay);
            messagesContainer.getChildren().add(messageContainer);
            scrollToBottom();
        });
    }

    public void addImmediateMessage(TextMessage message, Player player) {
        Platform.runLater(() -> {
            MessageContainer messageContainer = new MessageContainer(message, player);
            messagesContainer.getChildren().add(messageContainer);
            scrollToBottom();
        });
    }

    public void addMessageWithTypingIndicator(TextMessage message, Player player, EventHandler<MouseEvent> onReplay) {
        MessageContainer messageContainer = new MessageContainer(message, player, onReplay);
        messageQueue.add(messageContainer);
    }

    public void addMessageWithTypingIndicator(TextMessage message, Player player) {
        MessageContainer messageContainer = new MessageContainer(message, player);
        messageQueue.add(messageContainer);
    }

    public void clearMessages() {
        hideTextInput();
        hideButtonInput();
        messageQueue.clear();
        Platform.runLater(() -> messagesContainer.getChildren().clear());
    }

    public void showReplayButtonFor(UUID messageId) {
        Platform.runLater(() -> {
            messagesContainer.getChildren().stream()
                             .filter(node -> node instanceof MessageContainer)
                             .map(node -> (MessageContainer) node)
                             .filter(messageContainer -> messageContainer.isInteractable() && Objects.equals(messageContainer.getId(), messageId.toString()))
                             .map(messageContainer -> messageContainer.getChildren().stream()
                                                                      .filter(child -> child instanceof ReplayButton)
                                                                      .map(child -> (ReplayButton) child)
                                                                      .findFirst()
                                                                      .orElseThrow())
                             .forEach(replayButton -> replayButton.setVisible(true));
            scrollToBottom();
        });
    }

    private List<String> hideAllReplayButtons(CountDownLatch latch) {
        List<String> messageContainerIds = new ArrayList<>();
        Platform.runLater(() -> {
            messagesContainer.getChildren().stream()
                             .filter(node -> node instanceof MessageContainer)
                             .map(node -> (MessageContainer) node)
                             .filter(MessageContainer::isInteractable)
                             .forEach(messageContainer -> messageContainer.getChildren().stream()
                                 .filter(child -> child instanceof ReplayButton)
                                 .map(child -> (ReplayButton) child)
                                 .findFirst()
                                 .ifPresent(replayButton -> {
                                    replayButton.setVisible(false);
                                    messageContainerIds.add(messageContainer.getId());
                             }));
            scrollToBottom();
            latch.countDown();
        });
        return messageContainerIds;
    }

    private void showReplayButtonFor(List<String> messageContainerIds) {
        Platform.runLater(() -> {
            messagesContainer.getChildren().stream()
                             .filter(node -> node instanceof MessageContainer)
                             .map(node -> (MessageContainer) node)
                             .filter(messageContainer -> messageContainer.isInteractable() && messageContainerIds.contains(messageContainer.getId()))
                             .map(messageContainer -> messageContainer.getChildren().stream()
                                                                      .filter(child -> child instanceof ReplayButton)
                                                                      .map(child -> (ReplayButton) child)
                                                                      .findFirst()
                                                                      .orElseThrow())
                             .forEach(replayButton -> replayButton.setVisible(true));
            scrollToBottom();
        });
    }

    private long getMillisToWait(String text) {
        long minimum = 1000L;
        long waitTime = text.split(" ").length * 150L;
        return Math.max(minimum, waitTime);
    }

    private void processMessageQueue(CountDownLatch latch) {
        long keyFrameTime = 0L;
        long waitTime;
        Timeline timeline = new Timeline();

        while (!messageQueue.isEmpty()) {
            MessageContainer message = messageQueue.poll();
            waitTime = 0L;
            if (!message.isPlayerMessage()) {
                waitTime = getMillisToWait(message.getText());
            }

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(keyFrameTime), e -> showTypingIndicator()));

            keyFrameTime = keyFrameTime + waitTime;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(keyFrameTime), e -> {
                hideTypingIndicator();
                messagesContainer.getChildren().add(message);
                scrollToBottom();
            }));
        }

        timeline.setOnFinished(e -> latch.countDown());
        timeline.play();
    }

    private void showTypingIndicator() {
        Platform.runLater(() -> {
            TypingIndicator typingIndicator = new TypingIndicator();
            typingIndicator.setId("typing-indicator");
            messagesContainer.getChildren().add(typingIndicator);
            scrollToBottom();
        });
    }

    private void hideTypingIndicator() {
        Platform.runLater(() -> {
            messagesContainer.getChildren().removeIf(node -> node.getId().equals("typing-indicator"));
            scrollToBottom();
        });
    }

    private void scrollToBottom() {
        messagesScrollPane.applyCss();
        messagesScrollPane.layout();
        messagesScrollPane.setVvalue(messagesScrollPane.getVmax());
    }

    private static class MessageContainer extends HBox {
        private final boolean isPlayerMessage;
        private final boolean isInteractable;

        public MessageContainer(TextMessage message, Player player, EventHandler<MouseEvent> onReplay) {
            setId(message.getId().toString());
            addLabel(message, player);
            addReplayButton(message, onReplay);
            this.isPlayerMessage = message.isPlayerMessage();
            this.isInteractable = message.isInteractable();
        }

        public MessageContainer(TextMessage message, Player player) {
            setId(message.getId().toString());
            addLabel(message, player);
            this.isPlayerMessage = message.isPlayerMessage();
            this.isInteractable = false;
        }

        public boolean isPlayerMessage() {
            return isPlayerMessage;
        }

        public boolean isInteractable() {
            return isInteractable;
        }

        public String getText() {
            return ((Label) getChildren().getFirst()).getText();
        }

        private void addLabel(TextMessage message, Player player) {
            Label label = new Label(message.getInterpolatedText(player));

            if (message.isPlayerMessage()) {
                getStyleClass().add("user-message-container");
                label.getStyleClass().add("user-message");
            } else {
                getStyleClass().add("message-container");
                label.getStyleClass().add("message");
            }

            getChildren().add(label);
        }

        private void addReplayButton(TextMessage message, EventHandler<MouseEvent> onReplay) {
            if (message.isInteractable()) {
                getChildren().add(new ReplayButton(onReplay));
            }
        }
    }

    private static class ReplayButton extends HBox {
        public ReplayButton(EventHandler<MouseEvent> eventHandler) {
            getStyleClass().add("replay-button");
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ROTATE_LEFT);
            getChildren().add(icon);

            setOnMouseClicked(eventHandler);
            setVisible(false);
        }
    }

    public static class TypingIndicator extends HBox {
        public TypingIndicator() {
            getStyleClass().add("typing-indicator-container");
            FontAwesomeIconView dot1 = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
            FontAwesomeIconView dot2 = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
            FontAwesomeIconView dot3 = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
            getChildren().addAll(dot1, dot2, dot3);

            createBounceAnimation(dot1, 0).play();
            createBounceAnimation(dot2, 200).play();
            createBounceAnimation(dot3, 400).play();
        }

        private Timeline createBounceAnimation(FontAwesomeIconView dot, int delay) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(dot.translateYProperty(), 0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(dot.translateYProperty(), -10)),
                    new KeyFrame(Duration.millis(400), new KeyValue(dot.translateYProperty(), 0)),
                    new KeyFrame(Duration.millis(1000), new KeyValue(dot.translateYProperty(), 0))
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setDelay(Duration.millis(delay));
            return timeline;
        }
    }
}
