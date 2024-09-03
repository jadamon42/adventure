package com.github.jadamon42.adventure.common.state;

import com.github.jadamon42.adventure.common.model.Effect;
import com.github.jadamon42.adventure.common.model.Item;
import com.github.jadamon42.adventure.common.model.TextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckpointDelta {
    private final List<TextMessage> textMessages;
    private final PlayerDelta playerDelta;
    private final UUID currentNodeId;
    private final UUID currentMessageId;

    public CheckpointDelta(List<TextMessage> textMessages, PlayerDelta playerDelta, UUID currentNodeId, UUID currentMessageId) {
        this.textMessages = textMessages;
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

    public List<TextMessage> getMessages() {
        return textMessages;
    }

    public UUID getCurrentNodeId() {
        return currentNodeId;
    }

    public UUID getCurrentMessageId() {
        return currentMessageId;
    }

    public static class Builder {
        private final List<TextMessage> textMessages;
        private final PlayerDelta.Builder playerDeltaBuilder;
        private UUID currentNodeId;
        private UUID currentMessageId;

        public Builder() {
            textMessages = new ArrayList<>();
            playerDeltaBuilder = PlayerDelta.newBuilder();
            currentNodeId = null;
            currentMessageId = null;
        }

        public CheckpointDelta build() {
            return new CheckpointDelta(textMessages, playerDeltaBuilder.build(), currentNodeId, currentMessageId);
        }

        public void addMessage(TextMessage textMessage) {
            textMessages.add(textMessage);
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
    }

}
