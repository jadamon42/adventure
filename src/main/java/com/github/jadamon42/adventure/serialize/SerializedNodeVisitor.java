package com.github.jadamon42.adventure.serialize;

import com.github.jadamon42.adventure.node.StoryNode;

public interface SerializedNodeVisitor {
    StoryNode visit(SerializedAcquireEffectTextNode serializedNode);

    StoryNode visit(SerializedAcquireItemTextNode serializedNode);

    StoryNode visit(SerializedBranchNode serializedNode);

    StoryNode visit(SerializedChoiceTextInputNode serializedNode);

    StoryNode visit(SerializedExpositionalTextNode serializedNode);

    StoryNode visit(SerializedFreeTextInputNode serializedNode);

    StoryNode visit(SerializedSwitchNode serializedNode);
}
