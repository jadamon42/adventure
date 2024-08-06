package com.github.jadamon42.adventure.node;

import java.util.function.Consumer;

public class FreeTextInputNode extends LinkableTextNode {

    private final Consumer<String> textConsumer;

    public FreeTextInputNode(String prompt) {
        super(prompt);
        this.textConsumer = null;
    }

    public FreeTextInputNode(String prompt, Consumer<String> consumer) {
        super(prompt);
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
