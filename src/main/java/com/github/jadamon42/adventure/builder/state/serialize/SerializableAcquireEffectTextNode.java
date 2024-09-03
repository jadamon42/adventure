package com.github.jadamon42.adventure.builder.state.serialize;

import java.util.List;

public record SerializableAcquireEffectTextNode(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String text,
        List<String> previousConnectionIds,
        String nextConnectionId,
        String effectConnectionId
) implements SerializableFullyLinkableNode {

    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
