package com.github.jadamon42.adventure.runner.ui.element;

import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.model.TextMessage;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class MessageContainer extends HBox {
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
