package com.github.jadamon42.adventure.node;

import java.util.UUID;

public abstract class StoryNode {
    private final UUID id;

    protected StoryNode() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public abstract void accept(StoryNodeVisitor visitor);

    public abstract StoryNode getNextNode();

    public abstract <T extends  StoryNode> T then(T nextNode);
}
