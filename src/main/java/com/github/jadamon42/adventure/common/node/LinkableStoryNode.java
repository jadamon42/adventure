package com.github.jadamon42.adventure.common.node;

import java.util.UUID;

public abstract class LinkableStoryNode extends StoryNode implements Linkable {
    private StoryNode nextNode;

    protected LinkableStoryNode() {
        super();
    }

    protected LinkableStoryNode(UUID id) {
        super(id);
    }

    @Override
    public StoryNode getNextNode() {
        return nextNode;
    }

    @Override
    public <T extends  StoryNode> T then(T nextNode) {
        this.nextNode = nextNode;
        return nextNode;
    }
}
