package com.github.jadamon42.adventure.model;

import com.github.jadamon42.adventure.util.TextInterpolator;

import java.io.Serializable;
import java.util.UUID;

public class TextMessage implements Serializable {
    private final UUID id;
    private final String text;
    private final boolean isPlayerMessage;
    private final boolean isInteractable;

    public TextMessage(String text, boolean isPlayerMessage) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.isPlayerMessage = isPlayerMessage;
        this.isInteractable = false;
    }

    public TextMessage(String text, boolean isPlayerMessage, boolean isInteractable) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.isPlayerMessage = isPlayerMessage;
        this.isInteractable = isInteractable;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isPlayerMessage() {
        return isPlayerMessage;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public String getInterpolatedText(Player player) {
        return TextInterpolator.interpolate(text, player);
    }
}

