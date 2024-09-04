package com.github.jadamon42.adventure.builder.state.serialize;

import java.util.List;

public record SerializableNameCondition(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String text,
        String subtype,
        List<String> conditionConnectionIds
) implements SerializableNode {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}