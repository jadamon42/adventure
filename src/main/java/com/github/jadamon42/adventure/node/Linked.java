package com.github.jadamon42.adventure.node;

import java.io.Serializable;

public interface Linked extends Serializable {
    StoryNode getNextNode();
}
