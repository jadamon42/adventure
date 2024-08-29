package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableEffect(
        String id,
        double layoutX,
        double layoutY,
        String title,
        List<String> effectConnectionIds
) implements SerializableNode {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
