package com.github.jadamon42.adventure.builder.state.serialize;

import java.util.List;

public record SerializableBranchNode(
        String id,
        double layoutX,
        double layoutY,
        String title,
        List<String> previousConnectionIds,
        List<SerializableConditional> branches
) implements SerializableNodeWithPreviousLinks {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
