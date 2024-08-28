package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;

public class LinkedConditionalText extends ConditionalText implements Linked {
    private final StoryNode nextNode;

    public LinkedConditionalText(String text, StoryNode nextNode) {
        super(text);
        this.nextNode = nextNode;
    }

    public LinkedConditionalText(String text, StoryNode nextNode, BooleanFunction<Player> condition) {
        super(text, condition);
        this.nextNode = nextNode;
    }

    public LinkedConditionalText(String text) {
        super(text);
        this.nextNode = null;
    }

    public LinkedConditionalText(String text, BooleanFunction<Player> condition) {
        super(text, condition);
        this.nextNode = null;
    }

    @Override
    public StoryNode getNextNode() {
        return nextNode;
    }
}
