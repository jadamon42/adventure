package com.github.jadamon42.adventure.builder.state;

interface SerializableNodeVisitor {
    void visit(SerializableAcquireEffectTextNode serializableNode);

    void visit(SerializableAcquireItemTextNode serializableNode);

    void visit(SerializableAnd serializableNode);

    void visit(SerializableBranchNode serializableNode);

    void visit(SerializableChoiceTextInputNode serializableNode);

    void visit(SerializableEffect serializableNode);

    void visit(SerializableEffectCondition serializableNode);

    void visit(SerializableExpositionalTextNode serializableNode);

    void visit(SerializableFreeTextInputNode serializableNode);

    void visit(SerializableInputHandler serializableNode);

    void visit(SerializableItem serializableNode);

    void visit(SerializableItemCondition serializableNode);

    void visit(SerializableNameCondition serializableNode);

    void visit(SerializableOr serializableNode);

    void visit(SerializableStart serializableNode);

    void visit(SerializableSwitchNode serializableNode);
}
