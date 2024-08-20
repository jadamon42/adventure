package com.github.jadamon42.adventure.engine.ui;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.model.TextMessage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import com.jfoenix.controls.JFXButton;
import javafx.util.Duration;

import java.util.Objects;
import java.util.UUID;

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

    private TypingIndicator typingIndicator;

    @FXML
    public void initialize() {
        hideTextInput();
        hideButtonInput();
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
        Platform.runLater(() -> {
            hideButtonInput();
            textInputContainer.setVisible(true);
            textInputContainer.setManaged(true);
            textInput.setOnAction(eventHandler);
            textInput.requestFocus();
            scrollToBottom();
        });
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
        Platform.runLater(() -> {
            hideTextInput();
            buttonInputContainer.setVisible(true);
            buttonInputContainer.setManaged(true);
            scrollToBottom();
        });
    }

    public void addChoiceButton(String text, EventHandler<ActionEvent> eventHandler) {
        Platform.runLater(() -> {
            JFXButton button = new JFXButton(text);
            button.getStyleClass().add("choice-button");
            button.setOnAction(eventHandler);
            buttonInputContainer.getChildren().add(button);
        });
    }

    private long getMillisToWait(String text) {
        long minimum = 1000L;
        long waitTime = text.split(" ").length * 150L;
        return Math.max(minimum, waitTime);
    }

    public void addMessage(TextMessage message, Player player, EventHandler<ActionEvent> onReplay) {
        showTypingIndicator();
        try {
            Thread.sleep(getMillisToWait(message.getText()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        hideTypingIndicator();
        Platform.runLater(() -> {
            MessageContainer messageContainer = new MessageContainer(message, player, onReplay);
            messagesContainer.getChildren().add(messageContainer);
            scrollToBottom();
        });
    }

    private void showTypingIndicator() {
        Platform.runLater(() -> {
            typingIndicator = new TypingIndicator();
            typingIndicator.setId("typing-indicator");
            messagesContainer.getChildren().add(typingIndicator);
            scrollToBottom();
        });
    }

    private void hideTypingIndicator() {
        Platform.runLater(() -> {
            messagesContainer.getChildren().remove(typingIndicator);
            scrollToBottom();
        });
    }

    public void clearMessages() {
        Platform.runLater(() -> messagesContainer.getChildren().clear());
    }

    public void makeReplayButtonVisibleFor(UUID messageId) {
        Platform.runLater(() -> {
            messagesContainer.getChildren().stream()
                    .filter(node -> node instanceof MessageContainer)
                    .map(node -> (MessageContainer) node)
                    .filter(messageContainer -> Objects.equals(messageContainer.getId(), messageId.toString()))
                    .map(messageContainer -> messageContainer.getChildren().stream()
                            .filter(child -> child instanceof ReplayButton)
                            .map(child -> (ReplayButton) child)
                            .findFirst()
                            .orElseThrow())
                    .forEach(replayButton -> replayButton.setVisible(true));
            scrollToBottom();
        });
    }

    private void scrollToBottom() {
        messagesScrollPane.applyCss();
        messagesScrollPane.layout();
        messagesScrollPane.setVvalue(messagesScrollPane.getVmax());
    }

    private static class MessageContainer extends HBox {
        public MessageContainer(TextMessage message, Player player, EventHandler<ActionEvent> onReplay) {
            addLabel(message, player);
            addReplayButton(message, onReplay);
        }

        public MessageContainer(TextMessage message, Player player) {
            addLabel(message, player);
        }

        private void addLabel(TextMessage message, Player player) {
            setId(message.getId().toString());
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

        private void addReplayButton(TextMessage message, EventHandler<ActionEvent> onReplay) {
            if (message.isInteractable()) {
                getChildren().add(new ReplayButton(onReplay));
            }
        }
    }



    private static class ReplayButton extends Button {
        public ReplayButton(EventHandler<ActionEvent> eventHandler) {
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ROTATE_LEFT);
            setFocusTraversable(false);
            setBackground(Background.EMPTY);
            setBorder(Border.EMPTY);
            setGraphic(icon);
            setCursor(Cursor.HAND);
            setOnAction(eventHandler);
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
