package com.github.jadamon42.adventure.runner.ui;

import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.model.TextMessage;
import com.github.jadamon42.adventure.runner.ui.element.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

    private final Queue<MessageAction> messageQueue;

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
            HBox hBox = new HBox();
            JFXButton button = new JFXButton(text);
            button.getStyleClass().add("choice-button");
            button.setOnAction(eventHandler);
            hBox.getChildren().add(button);
            buttonInputContainer.getChildren().add(hBox);
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

    public void sleep(Duration duration) {
        messageQueue.add(MessageAction.of(new WaitAmount(duration)));
    }

    public void addMessageWithTypingIndicator(TextMessage message, Player player, EventHandler<MouseEvent> onReplay) {
        MessageContainer messageContainer = new MessageContainer(message, player, onReplay);
        messageQueue.add(MessageAction.of(messageContainer));
    }

    public void addMessageWithTypingIndicator(TextMessage message, Player player) {
        MessageContainer messageContainer = new MessageContainer(message, player);
        messageQueue.add(MessageAction.of(messageContainer));
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
        double keyFrameTime = 0;
        double waitTime;
        Timeline timeline = new Timeline();

        while (!messageQueue.isEmpty()) {
            MessageAction messageAction = messageQueue.poll();
            if (messageAction.getWaitMessage() != null) {
                WaitAmount waitAmount = messageAction.getWaitMessage();
                keyFrameTime += waitAmount.getDuration().toMillis();
                if (messageQueue.isEmpty()) {
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(keyFrameTime), e -> latch.countDown()));
                }
            }
            if (messageAction.getMessageContainer() != null) {
                MessageContainer message = messageAction.getMessageContainer();
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

}
