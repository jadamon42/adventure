package com.github.jadamon42.adventure.common.state.serialize;

import com.github.jadamon42.adventure.common.node.StoryNode;
import com.github.jadamon42.adventure.common.util.PlayerDeltaBiFunction;

import java.util.UUID;

public record SerializableFreeTextInputNode(
        UUID id,
        UUID messageId,
        UUID nextNodeId,
        PlayerDeltaBiFunction<Object> consumer
) implements SerializableNode {
    public StoryNode accept(SerializableNodeVisitor visitor) {
        return visitor.visit(this);
    }
}
