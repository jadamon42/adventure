package com.github.jadamon42.adventure.model;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
    private final UUID id;
    private final String text;
    private final boolean isPlayerMessage;

    public Message(String text, boolean isPlayerMessage) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.isPlayerMessage = isPlayerMessage;
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
}

