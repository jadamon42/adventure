package com.github.jadamon42.adventure.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.jadamon42.adventure.model.*;
import com.github.jadamon42.adventure.node.*;

import java.io.IOException;
import java.util.*;

public class GameStateDeserializer extends JsonDeserializer<GameState> implements SerializedNodeVisitor {
    private SerializedGameState serializedGameState;

    public GameStateDeserializer() {
        this.serializedGameState = null;
    }

    public Map<UUID, TextMessage> getMessageMap() {
        return serializedGameState.messageMap();
    }

    public Map<UUID, Item> getItemMap() {
        return serializedGameState.itemMap();
    }

    public Map<UUID, Effect> getEffectMap() {
        return serializedGameState.effectMap();
    }

    public StoryNode getNode(UUID nodeId) {
        StoryNode node = null;
        if (serializedGameState.nodeMap().containsKey(nodeId)) {
            node = serializedGameState.nodeMap().get(nodeId).accept(this);
        }
        return node;
    }

    @Override
    public GameState deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        serializedGameState = p.readValueAs(SerializedGameState.class);

        Checkpoint initialCheckpoint = new Checkpoint(new Player(), serializedGameState.getSerializedStartNode().accept(this));
        GameState gameState = new GameState(initialCheckpoint);
        for (SerializedCheckpointDelta serializedCheckpointDelta : serializedGameState.checkpointDeltas()) {
            List<TextMessage> messages = new ArrayList<>();
            for (UUID messageId : serializedCheckpointDelta.messageIds()) {
                messages.add(serializedGameState.messageMap().get(messageId));
            }
            List<Item> items = new ArrayList<>();
            for (UUID itemId : serializedCheckpointDelta.playerDelta().itemIds()) {
                items.add(serializedGameState.itemMap().get(itemId));
            }
            List<Effect> effects = new ArrayList<>();
            for (UUID effectId : serializedCheckpointDelta.playerDelta().effectIds()) {
                effects.add(serializedGameState.effectMap().get(effectId));
            }
            PlayerDelta playerDelta = new PlayerDelta(
                    serializedCheckpointDelta.playerDelta().name(),
                    items,
                    effects
            );
            CheckpointDelta checkpointDelta = new CheckpointDelta(
                    messages,
                    playerDelta,
                    serializedCheckpointDelta.currentNodeId(),
                    serializedCheckpointDelta.currentMessageId()
            );
            gameState.addCheckpoint(checkpointDelta);
        }

        return gameState;
    }

    @Override
    public AcquireEffectTextNode visit(SerializedAcquireEffectTextNode serializedNode) {
        AcquireEffectTextNode node = AcquireEffectTextNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }

    @Override
    public AcquireItemTextNode visit(SerializedAcquireItemTextNode serializedNode) {
        AcquireItemTextNode node = AcquireItemTextNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }

    @Override
    public BranchNode visit(SerializedBranchNode serializedNode) {
        return BranchNode.fromSerialized(serializedNode, this);
    }

    @Override
    public ChoiceTextInputNode visit(SerializedChoiceTextInputNode serializedNode) {
        return ChoiceTextInputNode.fromSerialized(serializedNode, this);
    }

    @Override
    public ExpositionalTextNode visit(SerializedExpositionalTextNode serializedNode) {
        ExpositionalTextNode node = ExpositionalTextNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }

    @Override
    public FreeTextInputNode visit(SerializedFreeTextInputNode serializedNode) {
        FreeTextInputNode node = FreeTextInputNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }

    @Override
    public SwitchNode visit(SerializedSwitchNode serializedNode) {
        SwitchNode node = SwitchNode.fromSerialized(serializedNode, this);
        node.then(getNode(serializedNode.nextNodeId()));
        return node;
    }
}
