package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.StoryNodeVisitor;

import java.util.function.Consumer;

public class FreeTextInputNode extends LinkableTextNode {

    private final Consumer<String> textConsumer;

    public FreeTextInputNode(String text) {
        super(text);
        this.textConsumer = null;
    }

    public FreeTextInputNode(String text, Consumer<String> consumer) {
        super(text);
        this.textConsumer = consumer;
    }

    public Consumer<String> getTextConsumer() {
        return textConsumer;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
