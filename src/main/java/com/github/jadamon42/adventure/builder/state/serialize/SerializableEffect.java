package com.github.jadamon42.adventure.builder.state.serialize;

import java.util.List;

public record SerializableEffect(
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
