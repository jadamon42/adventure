package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.common.node.StoryNode;

public interface StoryNodeTranslator extends VisitableNode {
    StoryNode toStoryNode();

    void clearCachedStoryNode();
}
