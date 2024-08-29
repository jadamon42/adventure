package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableItemCondition(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String itemConnectionId,
        List<String> conditionConnectionIds
) implements SerializableNode {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
