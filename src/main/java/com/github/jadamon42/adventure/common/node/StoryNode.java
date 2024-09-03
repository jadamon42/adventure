package com.github.jadamon42.adventure.common.node;

import java.util.UUID;

public abstract class StoryNode {
    private final UUID id;

    protected StoryNode() {
        id = UUID.randomUUID();
    }

    protected StoryNode(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public abstract void accept(StoryNodeVisitor visitor);
}
