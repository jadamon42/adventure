package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableOr(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String condition1ConnectionId,
        String condition2ConnectionId,
        List<String> conditionConnectionIds
) implements SerializableNode {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
