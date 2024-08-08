package com.github.jadamon42.adventure.node;

public abstract class LinkableStoryNode extends StoryNode {
    private StoryNode nextNode;

    protected LinkableStoryNode() {
        super();
    }

    public StoryNode getNextNode() {
        return nextNode;
    }

    public <T extends  StoryNode> T then(T nextNode) {
        this.nextNode = nextNode;
        return nextNode;
    }
}
