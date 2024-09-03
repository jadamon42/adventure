package com.github.jadamon42.adventure.common.node;

public interface Linkable {
    StoryNode getNextNode();
    <T extends  StoryNode> T then(T nextNode);
}
