package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableBranchNode(
        String id,
        double layoutX,
        double layoutY,
        String title,
        List<String> previousConnectionIds,
        List<SerializableOption> branches
) implements SerializableNodeWithPreviousLinks {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
