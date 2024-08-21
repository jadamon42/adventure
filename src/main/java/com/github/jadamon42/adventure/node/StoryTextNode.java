package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;

public abstract class StoryTextNode extends StoryNode {
    private final String text;

    public StoryTextNode(String text) {
        super();
        this.text = text;
    }

    protected String getText() {
        return text;
    }

    public abstract TextMessage getMessage();
}
