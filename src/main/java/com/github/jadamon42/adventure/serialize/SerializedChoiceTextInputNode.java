package com.github.jadamon42.adventure.serialize;

import com.github.jadamon42.adventure.node.StoryNode;

import java.util.List;
import java.util.UUID;

public record SerializedChoiceTextInputNode(
        UUID id,
        UUID messageId,
        List<SerializedChoice> choices
) implements SerializedNode {
    public StoryNode accept(SerializedNodeVisitor visitor) {
        return visitor.visit(this);
    }
}
