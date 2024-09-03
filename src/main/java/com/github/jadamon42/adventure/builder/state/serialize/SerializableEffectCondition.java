package com.github.jadamon42.adventure.builder.state.serialize;

import java.util.List;

public record SerializableEffectCondition(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String effectConnectionId,
        List<String> conditionConnectionIds
) implements SerializableNode {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
