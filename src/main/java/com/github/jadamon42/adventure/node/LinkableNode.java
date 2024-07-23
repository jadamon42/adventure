package com.github.jadamon42.adventure.node;

public interface LinkableNode {
    StoryNode getNextNode();

    <T extends  StoryNode> T then(T nextNode);
}
