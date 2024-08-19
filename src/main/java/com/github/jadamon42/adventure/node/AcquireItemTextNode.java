package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Item;
import com.github.jadamon42.adventure.model.TextMessage;

public class AcquireItemTextNode extends LinkableStoryTextNode {
    private final Item item;
    private final TextMessage message;

    public AcquireItemTextNode(String text, Item item) {
        super(text);
        this.item = item;
        this.message = new TextMessage(this.getText(), false);
    }

    public Item getItem() {
        return item;
    }

    @Override
    public TextMessage getMessage() {
        return message;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
