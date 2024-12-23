package com.github.jadamon42.adventure.common.state.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.jadamon42.adventure.common.model.*;
import com.github.jadamon42.adventure.common.node.*;
import com.github.jadamon42.adventure.common.state.Checkpoint;
import com.github.jadamon42.adventure.common.state.CheckpointDelta;
import com.github.jadamon42.adventure.common.state.GameState;
import com.github.jadamon42.adventure.common.state.PlayerDelta;

import java.io.IOException;
import java.util.*;

public class GameStateDeserializer extends JsonDeserializer<GameState> implements SerializableNodeVisitor {
    private SerializableGameState serializableGameState;
    private final Map<UUID, StoryNode> nodeMap = new HashMap<>();

    public GameStateDeserializer() {
        this.serializableGameState = null;
    }

    public Map<UUID, TextMessage> getMessageMap() {
        return serializableGameState.messageMap();
    }

    public Map<UUID, Item> getItemMap() {
        return serializableGameState.itemMap();
    }

    public Map<UUID, Effect> getEffectMap() {
        return serializableGameState.effectMap();
    }

    public StoryNode getNode(UUID nodeId) {
        StoryNode node = null;
        if (nodeMap.containsKey(nodeId)) {
            node = nodeMap.get(nodeId);
        } else if (serializableGameState.nodeMap().containsKey(nodeId)) {
            node = serializableGameState.nodeMap().get(nodeId).accept(this);
            nodeMap.put(nodeId, node);
        }
        return node;
    }

    @Override
    public GameState deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        serializableGameState = p.readValueAs(SerializableGameState.class);

        Checkpoint initialCheckpoint = new Checkpoint(new Player(), serializableGameState.getSerializedStartNode().accept(this));
        GameState gameState = new GameState(initialCheckpoint);
        for (SerializableCheckpointDelta serializableCheckpointDelta : serializableGameState.checkpointDeltas()) {
            List<TextMessage> messages = new ArrayList<>();
            for (UUID messageId : serializableCheckpointDelta.messageIds()) {
                messages.add(serializableGameState.messageMap().get(messageId));
            }
            List<CustomAttribute> customAttributes =
                    new ArrayList<>(serializableCheckpointDelta.playerDelta().customAttributes());
            List<Item> items = new ArrayList<>();
            for (UUID itemId : serializableCheckpointDelta.playerDelta().itemIds()) {
                items.add(serializableGameState.itemMap().get(itemId));
            }
            List<Effect> effects = new ArrayList<>();
            for (UUID effectId : serializableCheckpointDelta.playerDelta().effectIds()) {
                effects.add(serializableGameState.effectMap().get(effectId));
            }
            PlayerDelta playerDelta = new PlayerDelta(
                    serializableCheckpointDelta.playerDelta().name(),
                    customAttributes,
                    items,
                    effects
            );
            CheckpointDelta checkpointDelta = new CheckpointDelta(
                    messages,
                    playerDelta,
                    serializableCheckpointDelta.currentNodeId(),
                    serializableCheckpointDelta.currentMessageId()
            );
            gameState.addCheckpoint(checkpointDelta);
        }

        return gameState;
    }

    @Override
    public AcquireEffectTextNode visit(SerializableAcquireEffectTextNode serializedNode) {
        AcquireEffectTextNode node = AcquireEffectTextNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }

    @Override
    public AcquireItemTextNode visit(SerializableAcquireItemTextNode serializedNode) {
        AcquireItemTextNode node = AcquireItemTextNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }

    @Override
    public BranchNode visit(SerializableBranchNode serializedNode) {
        return BranchNode.fromSerialized(serializedNode, this);
    }

    @Override
    public ChoiceTextInputNode visit(SerializableChoiceTextInputNode serializedNode) {
        return ChoiceTextInputNode.fromSerialized(serializedNode, this);
    }

    @Override
    public ExpositionalTextNode visit(SerializableExpositionalTextNode serializedNode) {
        ExpositionalTextNode node = ExpositionalTextNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }

    @Override
    public FreeTextInputNode visit(SerializableFreeTextInputNode serializedNode) {
        FreeTextInputNode node = FreeTextInputNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }

    @Override
    public SwitchNode visit(SerializableSwitchNode serializedNode) {
        SwitchNode node = SwitchNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }

    @Override
    public StoryNode visit(SerializableWaitNode serializedNode) {
        WaitNode node = WaitNode.fromSerialized(serializedNode);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }
}
