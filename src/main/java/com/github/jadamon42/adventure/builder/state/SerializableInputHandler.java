package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableInputHandler(
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
