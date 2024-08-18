package com.github.jadamon42.adventure.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckpointDelta implements Serializable {
    private final Message[] messages;
    private final PlayerDelta playerDelta;
    private final UUID currentNodeId;
    private final UUID currentMessageId;

    public CheckpointDelta(List<Message> messages, PlayerDelta playerDelta, UUID currentNodeId, UUID currentMessageId) {
        this.messages = messages.toArray(new Message[0]);
        this.playerDelta = playerDelta;
        this.currentNodeId = currentNodeId;
        this.currentMessageId = currentMessageId;
    }

    public static CheckpointDelta.Builder newBuilder() {
        return new Builder();
    }

    public PlayerDelta getPlayerDelta() {
        return playerDelta;
    }

    public Message[] getMessages() {
        return messages;
    }

    public UUID getCurrentNodeId() {
        return currentNodeId;
    }

    public UUID getCurrentMessageId() {
        return currentMessageId;
    }

    public static class Builder {
        private final List<Message> messages;
        private final PlayerDelta.Builder playerDeltaBuilder;
        private UUID currentNodeId;
        private UUID currentMessageId;

        public Builder() {
            messages = new ArrayList<>();
            playerDeltaBuilder = PlayerDelta.newBuilder();
            currentNodeId = null;
            currentMessageId = null;
        }

        public CheckpointDelta build() {
            return new CheckpointDelta(messages, playerDeltaBuilder.build(), currentNodeId, currentMessageId);
        }

        public void addMessage(Message message) {
            messages.add(message);
        }

        public void applyPlayerDelta(PlayerDelta playerDelta) {
            if (playerDelta.getName() != null) {
                playerDeltaBuilder.setName(playerDelta.getName());
            }
            for (Item item : playerDelta.getItems()) {
                playerDeltaBuilder.addItem(item);
            }
            for (Effect effect : playerDelta.getEffects()) {
                playerDeltaBuilder.addEffect(effect);
            }
        }

        public void setCurrentNodeId(UUID currentNodeId) {
            this.currentNodeId = currentNodeId;
        }

        public void setCurrentMessageId(UUID currentMessageId) {
            this.currentMessageId = currentMessageId;
        }

        public Message getLatestMessage() {
            return messages.getLast();
        }
    }

}
