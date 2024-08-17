package com.github.jadamon42.adventure.node;

public interface Linkable extends Linked {
    <T extends  StoryNode> T then(T nextNode);
}
