package com.github.jadamon42.adventure.common.state;

import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.node.StoryNode;
import com.github.jadamon42.adventure.common.util.StoryNodeTraverser;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Checkpoint {
    private final Player player;
    private final HashMap<UUID, StoryNode> nodeMap;
    private final UUID currentNodeId;
    private final MessageHistory messageHistory;

    public Checkpoint(Player player, StoryNode startNode) {
        this.player = new Player(player);
        StoryNodeTraverser traverser = new StoryNodeTraverser();
        this.nodeMap = traverser.getStoryNodeMap(startNode);
        this.currentNodeId = startNode.getId();
        this.messageHistory = new MessageHistory();
    }

    private Checkpoint(Player player, HashMap<UUID, StoryNode> nodeMap, UUID currentNodeId, MessageHistory messageHistory) {
        this.player = player;
        this.nodeMap = nodeMap;
        this.currentNodeId = currentNodeId;
        this.messageHistory = messageHistory;
    }

    public Player getPlayer() {
        return new Player(player);
    }

    public UUID getCurrentNodeId() {
        return currentNodeId;
    }

    public StoryNode getCurrentNode() {
        return nodeMap.get(currentNodeId);
    }

    public MessageHistory getMessageHistory() {
        return new MessageHistory(messageHistory);
    }

    public Checkpoint apply(CheckpointDelta checkpointDelta) {
        Builder builder = new Builder(player, messageHistory, currentNodeId);
        builder.apply(checkpointDelta);
        return builder.build();
    }

    public Checkpoint applyAll(List<CheckpointDelta> checkpointDeltas) {
        Builder builder = new Builder(player, messageHistory, currentNodeId);
        for (CheckpointDelta checkpointDelta : checkpointDeltas) {
            builder.apply(checkpointDelta);
        }
        return builder.build();
    }

    public class Builder {
        private final Player player;
        private UUID currentNodeId;
        private final MessageHistory messageHistory;

        public Builder(Player player, MessageHistory messageHistory, UUID currentNodeId) {
            this.player = new Player(player);
            this.messageHistory = new MessageHistory(messageHistory);
            this.currentNodeId = currentNodeId;
        }

        public void apply(CheckpointDelta checkpointDelta) {
            player.apply(checkpointDelta.getPlayerDelta());
            messageHistory.addAll(checkpointDelta.getMessages());
            currentNodeId = checkpointDelta.getCurrentNodeId();
        }

        public Checkpoint build() {
            return new Checkpoint(player, nodeMap, currentNodeId, messageHistory);
        }
    }
}
