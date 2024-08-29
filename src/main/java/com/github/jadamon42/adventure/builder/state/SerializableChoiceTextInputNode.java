package com.github.jadamon42.adventure.builder.state;

import java.util.List;

record SerializableChoiceTextInputNode(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String text,
        List<String> previousConnectionIds,
        List<SerializableOption> choices
) implements SerializableNodeWithPreviousLinks {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
