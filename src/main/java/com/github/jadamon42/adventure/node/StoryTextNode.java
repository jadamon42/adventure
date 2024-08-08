package com.github.jadamon42.adventure.node;

public abstract class StoryTextNode extends StoryNode {
    private final String text;

    public StoryTextNode(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
