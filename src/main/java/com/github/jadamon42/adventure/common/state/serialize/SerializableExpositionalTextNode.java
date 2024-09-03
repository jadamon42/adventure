package com.github.jadamon42.adventure.common.state.serialize;

import com.github.jadamon42.adventure.common.node.StoryNode;

import java.util.UUID;

public record SerializableExpositionalTextNode(
        UUID id,
        UUID messageId,
        UUID nextNodeId
) implements SerializableNode {
    public StoryNode accept(SerializableNodeVisitor visitor) {
        return visitor.visit(this);
    }
}
