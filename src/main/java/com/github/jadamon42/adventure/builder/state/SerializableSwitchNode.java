package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableSwitchNode(
        String id,
        double layoutX,
        double layoutY,
        String title,
        List<String> previousConnectionIds,
        String nextConnectionId,
        List<SerializableOption> cases
) implements SerializableFullyLinkableNode {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
