package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Item;

public class AcquireItemTextNode extends LinkableStoryTextNode {
    private final Item item;

    public AcquireItemTextNode(String text, Item item) {
        super(text);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
