package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;

import java.util.function.Function;

public class LinkedTextChoice extends TextChoice {
    private final StoryNode nextNode;

    public LinkedTextChoice(String text, StoryNode nextNode) {
        super(text);
        this.nextNode = nextNode;
    }

    public LinkedTextChoice(String text, StoryNode nextNode, Function<Player, Boolean> condition) {
        super(text, condition);
        this.nextNode = nextNode;
    }

    public LinkedTextChoice(String text) {
        super(text);
        this.nextNode = null;
    }

    public LinkedTextChoice(String text, Function<Player, Boolean> condition) {
        super(text, condition);
        this.nextNode = null;
    }

    public StoryNode getNextNode() {
        return nextNode;
    }
}
