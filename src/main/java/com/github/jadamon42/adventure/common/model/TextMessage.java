package com.github.jadamon42.adventure.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jadamon42.adventure.common.util.TextInterpolator;

import java.util.UUID;

public class TextMessage {
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

    @JsonCreator
    TextMessage(@JsonProperty("id") UUID id, @JsonProperty("text") String text, @JsonProperty("isPlayerMessage") boolean isPlayerMessage, @JsonProperty("isInteractable") boolean isInteractable) {
        this.id = id;
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

    @JsonProperty("isPlayerMessage")
    public boolean isPlayerMessage() {
        return isPlayerMessage;
    }

    @JsonProperty("isInteractable")
    public boolean isInteractable() {
        return isInteractable;
    }

    public String getInterpolatedText(Player player) {
        return TextInterpolator.interpolate(text, player);
    }
}

