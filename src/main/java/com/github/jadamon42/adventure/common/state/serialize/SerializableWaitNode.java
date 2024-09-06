package com.github.jadamon42.adventure.common.state.serialize;

import com.github.jadamon42.adventure.common.node.StoryNode;

import java.util.UUID;

public record SerializableWaitNode(
        UUID id,
        UUID nextNodeId,
        long seconds
) implements SerializableNode {
    @Override
    public StoryNode accept(SerializableNodeVisitor visitor) {
        return visitor.visit(this);
    }
}
