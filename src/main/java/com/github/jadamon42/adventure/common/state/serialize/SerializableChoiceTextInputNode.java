package com.github.jadamon42.adventure.common.state.serialize;

import com.github.jadamon42.adventure.common.node.StoryNode;

import java.util.List;
import java.util.UUID;

public record SerializableChoiceTextInputNode(
        UUID id,
        UUID messageId,
        List<SerializableConditional> choices
) implements SerializableNode {
    public StoryNode accept(SerializableNodeVisitor visitor) {
        return visitor.visit(this);
    }
}
