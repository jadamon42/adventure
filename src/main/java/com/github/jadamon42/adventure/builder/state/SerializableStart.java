package com.github.jadamon42.adventure.builder.state;

record SerializableStart(
        String id,
        double layoutX,
        double layoutY,
        String title,
        String nextConnectionId
) implements SerializableNodeWithNextLink {
    @Override
    public void accept(SerializableNodeVisitor visitor) {
        visitor.visit(this);
    }
}
