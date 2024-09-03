package com.github.jadamon42.adventure.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.jadamon42.adventure.model.*;
import com.github.jadamon42.adventure.node.*;

import java.io.IOException;
import java.util.*;

public class GameStateSerializer extends JsonSerializer<GameState> implements StoryNodeVisitor {
    Map<UUID, Item> itemMap;
    Map<UUID, Effect> effectMap;
    Map<UUID, SerializedNode> nodeMap;
    Map<UUID, TextMessage> messageMap;
    UUID initialNodeId;
    List<SerializedCheckpointDelta> serializedCheckpointDeltas;

    public GameStateSerializer() {
        this.itemMap = new HashMap<>();
        this.effectMap = new HashMap<>();
        this.nodeMap = new HashMap<>();
        this.messageMap = new HashMap<>();
        this.initialNodeId = null;
        this.serializedCheckpointDeltas = new ArrayList<>();
    }

    @Override
    public void serialize(GameState gameState, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        gameState.getInitialCheckpoint().getCurrentNode().accept(this);
        buildSerializedCheckpointDeltas(gameState.getCheckpointDeltas());
        initialNodeId = gameState.getInitialCheckpoint().getCurrentNodeId();
        jsonGenerator.writeObject(buildSerializedGameState());
    }

    @Override
    public void visit(ExpositionalTextNode node) {
        SerializedExpositionalTextNode serializedNode = new SerializedExpositionalTextNode(
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
        List<SerializedChoice> choices = new ArrayList<>();
        for (LinkedConditionalText choice : node.getAllChoices()) {
            SerializedChoice serializedChoice = new SerializedChoice(
                    choice.getMessage().getId(),
                    getNextNodeId(choice),
                    choice.getCondition()
            );
            messageMap.put(choice.getMessage().getId(), choice.getMessage());
            visitNext(choice);
            choices.add(serializedChoice);
        }
        SerializedChoiceTextInputNode serializedNode = new SerializedChoiceTextInputNode(
                node.getId(),
                node.getMessage().getId(),
                choices
        );
        nodeMap.put(node.getId(), serializedNode);
        messageMap.put(node.getMessage().getId(), node.getMessage());
    }

    @Override
    public void visit(FreeTextInputNode node) {
        SerializedFreeTextInputNode serializedNode = new SerializedFreeTextInputNode(
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
        SerializedAcquireItemTextNode serializedNode = new SerializedAcquireItemTextNode(
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
        SerializedAcquireEffectTextNode serializedNode = new SerializedAcquireEffectTextNode(
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
        List<SerializedChoice> choices = new ArrayList<>();
        for (LinkedConditionalText choice : node.getAllChoices()) {
            SerializedChoice serializedChoice = new SerializedChoice(
                    choice.getMessage().getId(),
                    getNextNodeId(choice),
                    choice.getCondition()
            );
            messageMap.put(choice.getMessage().getId(), choice.getMessage());
            visitNext(choice);
            choices.add(serializedChoice);
        }
        SerializedBranchNode serializedNode = new SerializedBranchNode(
                node.getId(),
                choices
        );
        nodeMap.put(node.getId(), serializedNode);
    }

    @Override
    public void visit(SwitchNode node) {
        List<SerializedChoice> choices = new ArrayList<>();
        for (ConditionalText choice : node.getAllChoices()) {
            SerializedChoice serializedChoice = new SerializedChoice(
                    choice.getMessage().getId(),
                    null,
                    choice.getCondition()
            );
            messageMap.put(choice.getMessage().getId(), choice.getMessage());
            choices.add(serializedChoice);
        }
        SerializedSwitchNode serializedNode = new SerializedSwitchNode(
                node.getId(),
                node.getNextNode().getId(),
                choices
        );
        nodeMap.put(node.getId(), serializedNode);
        visitNext(node);
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
            SerializedPlayerDelta serializedPlayerDelta = new SerializedPlayerDelta(
                    delta.getPlayerDelta().getName(),
                    itemIds,
                    effectIds
            );
            serializedCheckpointDeltas.add(new SerializedCheckpointDelta(
                    messageIds,
                    serializedPlayerDelta,
                    delta.getCurrentNodeId(),
                    delta.getCurrentMessageId()
            ));
        }
    }

    private SerializedGameState buildSerializedGameState() {
        return new SerializedGameState(
                itemMap,
                effectMap,
                nodeMap,
                messageMap,
                initialNodeId,
                serializedCheckpointDeltas);
    }
}
