package com.github.jadamon42.adventure.model;

import com.github.jadamon42.adventure.node.StoryNode;

import java.util.function.Function;

public class LinkableTextChoice extends TextChoice {
    private final StoryNode nextNode;

    public LinkableTextChoice(String text, StoryNode nextNode) {
        super(text);
        this.nextNode = nextNode;
    }

    public LinkableTextChoice(String text, StoryNode nextNode, Function<Player, Boolean> condition) {
        super(text, condition);
        this.nextNode = nextNode;
    }

    public LinkableTextChoice(String text) {
        super(text);
        this.nextNode = null;
    }

    public LinkableTextChoice(String text, Function<Player, Boolean> condition) {
        super(text, condition);
        this.nextNode = null;
    }

    public StoryNode getNextNode() {
        return nextNode;
    }
}
