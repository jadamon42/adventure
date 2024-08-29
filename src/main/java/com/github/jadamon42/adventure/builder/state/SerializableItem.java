package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableItem(
        String id,
        double layoutX,
        double layoutY,
        String title,
        List<String> itemConnectionIds
) implements SerializableNode {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
