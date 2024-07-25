package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.StoryNodeVisitor;

public abstract class LinkableTextNode extends StoryNode implements TextNode {
    private final String text;
    private StoryNode nextNode;

    public LinkableTextNode(String text) {
        this.text = text;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getText() {
        return text;
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
