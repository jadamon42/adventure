package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;

public class ExpositionalTextNode extends LinkableStoryTextNode {
    private final TextMessage message;

    public ExpositionalTextNode(String text) {
        super(text);
        this.message = new TextMessage(this.getText(), false);
    }

    @Override
    public TextMessage getMessage() {
        return message;
    }
}
