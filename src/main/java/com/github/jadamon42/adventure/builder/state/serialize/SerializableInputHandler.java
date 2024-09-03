package com.github.jadamon42.adventure.builder.state.serialize;

import java.util.List;

public record SerializableInputHandler(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String subtype,
        List<String> inputHandlerConnectionIds
) implements SerializableNode {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
