package com.github.jadamon42.adventure.builder.state.serialize;

import java.util.List;

public record SerializableWaitNode (
        String id,
        double layoutX,
        double layoutY,
        String title,
        List<String> previousConnectionIds,
        String nextConnectionId,
        long seconds
) implements SerializableFullyLinkableNode  {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
