package com.github.jadamon42.adventure.node;

public abstract class LinkableStoryTextNode extends StoryTextNode {
    private StoryNode nextNode;

    protected LinkableStoryTextNode(String text) {
        super(text);
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }

    public StoryNode getNextNode() {
        return nextNode;
    }

    public <T extends  StoryNode> T then(T nextNode) {
        this.nextNode = nextNode;
        return nextNode;
    }
}
