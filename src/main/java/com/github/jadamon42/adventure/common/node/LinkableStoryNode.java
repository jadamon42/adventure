package com.github.jadamon42.adventure.common.node;

public abstract class LinkableStoryNode extends StoryNode implements Linkable {
    private StoryNode nextNode;

    protected LinkableStoryNode() {
        super();
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
