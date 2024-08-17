package com.github.jadamon42.adventure.node;

import java.io.Serializable;
import java.util.UUID;

public abstract class StoryNode implements Serializable {
    private final UUID id;

    protected StoryNode() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public abstract void accept(StoryNodeVisitor visitor);
}
