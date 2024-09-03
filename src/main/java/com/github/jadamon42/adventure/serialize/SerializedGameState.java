package com.github.jadamon42.adventure.serialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jadamon42.adventure.model.Effect;
import com.github.jadamon42.adventure.model.Item;
import com.github.jadamon42.adventure.model.TextMessage;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record SerializedGameState(
    Map<UUID, Item> itemMap,
    Map<UUID, Effect> effectMap,
    Map<UUID, SerializedNode> nodeMap,
    Map<UUID, TextMessage> messageMap,
    UUID startNodeId,
    List<SerializedCheckpointDelta> checkpointDeltas) {
    @JsonIgnore
    public SerializedNode getSerializedStartNode() {
        return nodeMap.get(startNodeId);
    }
}
