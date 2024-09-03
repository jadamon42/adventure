package com.github.jadamon42.adventure.node;

import java.io.Serializable;

public interface Linkable extends Serializable {
    StoryNode getNextNode();
    <T extends  StoryNode> T then(T nextNode);
}
