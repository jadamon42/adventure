package com.github.jadamon42.adventure.common.state.serialize;

import com.github.jadamon42.adventure.common.model.Effect;
import com.github.jadamon42.adventure.common.model.Item;
import com.github.jadamon42.adventure.common.model.TextMessage;
import com.github.jadamon42.adventure.common.node.*;
import com.github.jadamon42.adventure.common.state.CheckpointDelta;
import com.github.jadamon42.adventure.common.state.GameState;

import java.util.*;

public class SerializableGameStateBuilder implements StoryNodeVisitor  {
    Map<UUID, Item> itemMap;
    Map<UUID, Effect> effectMap;
    Map<UUID, SerializableNode> nodeMap;
    Map<UUID, TextMessage> messageMap;
    UUID initialNodeId;
    List<SerializableCheckpointDelta> serializableCheckpointDeltas;

    public SerializableGameStateBuilder() {
        this.itemMap = new HashMap<>();
        this.effectMap = new HashMap<>();
        this.nodeMap = new HashMap<>();
        this.messageMap = new HashMap<>();
        this.initialNodeId = null;
        this.serializableCheckpointDeltas = new ArrayList<>();
    }

    public SerializableGameState build(GameState gameState) {
        gameState.getInitialCheckpoint().getCurrentNode().accept(this);
        buildSerializedCheckpointDeltas(gameState.getCheckpointDeltas());
        initialNodeId = gameState.getInitialCheckpoint().getCurrentNodeId();
        return buildSerializedGameState();
    }

    @Override
    public void visit(ExpositionalTextNode node) {
        SerializableExpositionalTextNode serializedNode = new SerializableExpositionalTextNode(
                node.getId(),
                node.getMessage().getId(),
                getNextNodeId(node)
        );
        nodeMap.put(node.getId(), serializedNode);
        messageMap.put(node.getMessage().getId(), node.getMessage());
        visitNext(node);
    }

    @Override
    public void visit(ChoiceTextInputNode node) {
        List<SerializableConditional> choices = new ArrayList<>();
        for (LinkedConditionalText choice : node.getAllChoices()) {
            SerializableConditional serializableConditional = new SerializableConditional(
                    choice.getMessage().getId(),
                    getNextNodeId(choice),
                    choice.getCondition()
            );
            messageMap.put(choice.getMessage().getId(), choice.getMessage());
            visitNext(choice);
            choices.add(serializableConditional);
        }
        SerializableChoiceTextInputNode serializedNode = new SerializableChoiceTextInputNode(
                node.getId(),
                node.getMessage().getId(),
                choices
        );
        nodeMap.put(node.getId(), serializedNode);
        messageMap.put(node.getMessage().getId(), node.getMessage());
    }

    @Override
    public void visit(FreeTextInputNode node) {
        SerializableFreeTextInputNode serializedNode = new SerializableFreeTextInputNode(
                node.getId(),
                node.getMessage().getId(),
                getNextNodeId(node),
                node.getTextConsumer()
        );
        nodeMap.put(node.getId(), serializedNode);
        messageMap.put(node.getMessage().getId(), node.getMessage());
        visitNext(node);

    }

    @Override
    public void visit(AcquireItemTextNode node) {
        SerializableAcquireItemTextNode serializedNode = new SerializableAcquireItemTextNode(
                node.getId(),
                node.getMessage().getId(),
                getNextNodeId(node),
                node.getItem().getId()
        );
        nodeMap.put(node.getId(), serializedNode);
        messageMap.put(node.getMessage().getId(), node.getMessage());
        itemMap.put(node.getItem().getId(), node.getItem());
        visitNext(node);
    }

    @Override
    public void visit(AcquireEffectTextNode node) {
        SerializableAcquireEffectTextNode serializedNode = new SerializableAcquireEffectTextNode(
                node.getId(),
                node.getMessage().getId(),
                getNextNodeId(node),
                node.getEffect().getId()
        );
        nodeMap.put(node.getId(), serializedNode);
        messageMap.put(node.getMessage().getId(), node.getMessage());
        effectMap.put(node.getEffect().getId(), node.getEffect());
        visitNext(node);
    }

    @Override
    public void visit(BranchNode node) {
        List<SerializableConditional> choices = new ArrayList<>();
        for (LinkedConditionalText choice : node.getAllBranches()) {
            SerializableConditional serializableConditional = new SerializableConditional(
                    choice.getMessage().getId(),
                    getNextNodeId(choice),
                    choice.getCondition()
            );
            messageMap.put(choice.getMessage().getId(), choice.getMessage());
            visitNext(choice);
            choices.add(serializableConditional);
        }
        SerializableBranchNode serializedNode = new SerializableBranchNode(
                node.getId(),
                choices
        );
        nodeMap.put(node.getId(), serializedNode);
    }

    @Override
    public void visit(SwitchNode node) {
        List<SerializableConditional> choices = new ArrayList<>();
        for (ConditionalText choice : node.getAllCases()) {
            SerializableConditional serializableConditional = new SerializableConditional(
                    choice.getMessage().getId(),
                    null,
                    choice.getCondition()
            );
            messageMap.put(choice.getMessage().getId(), choice.getMessage());
            choices.add(serializableConditional);
        }
        SerializableSwitchNode serializedNode = new SerializableSwitchNode(
                node.getId(),
                node.getNextNode().getId(),
                choices
        );
        nodeMap.put(node.getId(), serializedNode);
        visitNext(node);
    }

    @Override
    public void visit(WaitNode waitNode) {
        SerializableWaitNode serializedNode = new SerializableWaitNode(
                waitNode.getId(),
                getNextNodeId(waitNode),
                waitNode.getDuration().getSeconds()
        );
        nodeMap.put(waitNode.getId(), serializedNode);
        visitNext(waitNode);
    }

    private UUID getNextNodeId(Linkable linkable) {
        return linkable.getNextNode() != null ? linkable.getNextNode().getId() : null;
    }

    private void visitNext(Linkable linkable) {
        if (linkable.getNextNode() != null) {
            linkable.getNextNode().accept(this);
        }
    }

    private void buildSerializedCheckpointDeltas(List<CheckpointDelta> checkpointDeltas) {
        for (CheckpointDelta delta : checkpointDeltas) {
            List<UUID> messageIds = new ArrayList<>();
            for (TextMessage message : delta.getMessages()) {
                if (!messageMap.containsKey(message.getId())) {
                    // capture all user input messages
                    // TODO: better way to do this?
                    // if we separate text from messages, message IDs won't align with nodes and checkpoints. I think this is a required evil
                    messageMap.put(message.getId(), message);
                }
                messageIds.add(message.getId());
            }
            List<UUID> itemIds = new ArrayList<>();
            for (Item item : delta.getPlayerDelta().getItems()) {
                itemIds.add(item.getId());
            }
            List<UUID> effectIds = new ArrayList<>();
            for (Effect effect : delta.getPlayerDelta().getEffects()) {
                effectIds.add(effect.getId());
            }
            SerializablePlayerDelta serializablePlayerDelta = new SerializablePlayerDelta(
                    delta.getPlayerDelta().getName(),
                    itemIds,
                    effectIds
            );
            serializableCheckpointDeltas.add(new SerializableCheckpointDelta(
                    messageIds,
                    serializablePlayerDelta,
                    delta.getCurrentNodeId(),
                    delta.getCurrentMessageId()
            ));
        }
    }

    private SerializableGameState buildSerializedGameState() {
        return new SerializableGameState(
                itemMap,
                effectMap,
                nodeMap,
                messageMap,
                initialNodeId,
                serializableCheckpointDeltas);
    }
}
