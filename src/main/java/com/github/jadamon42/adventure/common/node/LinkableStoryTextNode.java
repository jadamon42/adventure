package com.github.jadamon42.adventure.common.node;

import java.util.UUID;

public abstract class LinkableStoryTextNode extends StoryTextNode implements Linkable {
    private StoryNode nextNode;

    protected LinkableStoryTextNode(String text) {
        super(text);
    }

    protected LinkableStoryTextNode(UUID id, String text) {
        super(id, text);
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
