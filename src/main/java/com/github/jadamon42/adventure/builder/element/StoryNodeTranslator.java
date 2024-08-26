package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.node.StoryNode;

public interface StoryNodeTranslator {
    <T extends StoryNode> T toStoryNode();
}
