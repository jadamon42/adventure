package com.github.jadamon42.adventure.common.state.serialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jadamon42.adventure.common.model.Effect;
import com.github.jadamon42.adventure.common.model.Item;
import com.github.jadamon42.adventure.common.model.TextMessage;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record SerializableGameState(
    Map<UUID, Item> itemMap,
    Map<UUID, Effect> effectMap,
    Map<UUID, SerializableNode> nodeMap,
    Map<UUID, TextMessage> messageMap,
    UUID startNodeId,
    List<SerializableCheckpointDelta> checkpointDeltas) {
    @JsonIgnore
    public SerializableNode getSerializedStartNode() {
        return nodeMap.get(startNodeId);
    }
}
