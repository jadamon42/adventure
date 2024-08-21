package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.SerializableFunction;

public class LinkedTextChoice extends LinkedConditionalText {
    public LinkedTextChoice(String text, StoryNode nextNode) {
        super(text, nextNode);
    }

    public LinkedTextChoice(String text, StoryNode nextNode, SerializableFunction<Player, Boolean> condition) {
        super(text, nextNode, condition);
    }

    @Override
    protected TextMessage createMessage() {
        return new TextMessage(getText(), true);
    }
}
