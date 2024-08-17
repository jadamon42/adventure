package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.SerializableFunction;

import java.util.function.Function;

public class LinkedTextChoice extends TextChoice implements Linked {
    private final StoryNode nextNode;

    public LinkedTextChoice(String text, StoryNode nextNode) {
        super(text);
        this.nextNode = nextNode;
    }

    public LinkedTextChoice(String text, StoryNode nextNode, SerializableFunction<Player, Boolean> condition) {
        super(text, condition);
        this.nextNode = nextNode;
    }

    public LinkedTextChoice(String text) {
        super(text);
        this.nextNode = null;
    }

    public LinkedTextChoice(String text, SerializableFunction<Player, Boolean> condition) {
        super(text, condition);
        this.nextNode = null;
    }

    @Override
    public StoryNode getNextNode() {
        return nextNode;
    }
}
