package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableExpositionalTextNode(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String text,
        List<String> previousConnectionIds,
        String nextConnectionId
) implements SerializableFullyLinkableNode {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
