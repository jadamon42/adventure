package com.github.jadamon42.adventure.common.state.serialize;

import com.github.jadamon42.adventure.common.node.StoryNode;

public interface SerializableNodeVisitor {
    StoryNode visit(SerializableAcquireEffectTextNode serializedNode);

    StoryNode visit(SerializableAcquireItemTextNode serializedNode);

    StoryNode visit(SerializableBranchNode serializedNode);

    StoryNode visit(SerializableChoiceTextInputNode serializedNode);

    StoryNode visit(SerializableExpositionalTextNode serializedNode);

    StoryNode visit(SerializableFreeTextInputNode serializedNode);

    StoryNode visit(SerializableSwitchNode serializedNode);
}
