package com.github.jadamon42.adventure.builder.state.serialize;

import java.util.List;

public record SerializableChoiceTextInputNode(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String text,
        List<String> previousConnectionIds,
        List<SerializableConditional> choices
) implements SerializableNodeWithPreviousLinks {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
