package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableNameCondition(
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
