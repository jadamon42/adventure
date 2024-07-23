package com.github.jadamon42.adventure.model;

import com.github.jadamon42.adventure.node.StoryNode;

import java.util.function.Function;

public class TextChoice {
    private final String text;
    private final StoryNode nextNode;
    private final Function<Player, Boolean> condition;

    public TextChoice(String text, StoryNode nextNode) {
        this.text = text;
        this.nextNode = nextNode;
        this.condition = player -> true;
    }

    public TextChoice(String text, StoryNode nextNode, Function<Player, Boolean> condition) {
        this.text = text;
        this.nextNode = nextNode;
        this.condition = condition;
    }

    public boolean isAvailable(Player player) {
        return condition.apply(player);
    }

    public String getText() {
        return text;
    }

    public StoryNode getNextNode() {
        return nextNode;
    }
}
