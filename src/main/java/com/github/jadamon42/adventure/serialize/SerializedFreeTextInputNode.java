package com.github.jadamon42.adventure.serialize;

import com.github.jadamon42.adventure.node.StoryNode;
import com.github.jadamon42.adventure.util.PlayerDeltaBiFunction;

import java.util.UUID;

public record SerializedFreeTextInputNode(
        UUID id,
        UUID messageId,
        UUID nextNodeId,
        PlayerDeltaBiFunction<Object> consumer
) implements SerializedNode {
    public StoryNode accept(SerializedNodeVisitor visitor) {
        return visitor.visit(this);
    }
}
