package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.SerializableFunction;

public class LinkedConditionalText extends ConditionalText implements Linked {
    private final StoryNode nextNode;

    public LinkedConditionalText(String text, StoryNode nextNode) {
        super(text);
        this.nextNode = nextNode;
    }

    public LinkedConditionalText(String text, StoryNode nextNode, SerializableFunction<Player, Boolean> condition) {
        super(text, condition);
        this.nextNode = nextNode;
    }

    public LinkedConditionalText(String text) {
        super(text);
        this.nextNode = null;
    }

    public LinkedConditionalText(String text, SerializableFunction<Player, Boolean> condition) {
        super(text, condition);
        this.nextNode = null;
    }

    @Override
    public StoryNode getNextNode() {
        return nextNode;
    }
}
