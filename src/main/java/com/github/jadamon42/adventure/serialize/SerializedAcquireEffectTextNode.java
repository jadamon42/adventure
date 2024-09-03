package com.github.jadamon42.adventure.serialize;

import com.github.jadamon42.adventure.node.StoryNode;

import java.util.UUID;

public record SerializedAcquireEffectTextNode(
        UUID id,
        UUID messageId,
        UUID nextNodeId,
        UUID effectId
) implements SerializedNode {
    public StoryNode accept(SerializedNodeVisitor visitor) {
        return visitor.visit(this);
    }
}
