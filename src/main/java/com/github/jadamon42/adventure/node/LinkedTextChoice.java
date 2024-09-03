package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.serialize.SerializedChoice;
import com.github.jadamon42.adventure.util.BooleanFunction;

public class LinkedTextChoice extends LinkedConditionalText {
    public LinkedTextChoice(String text, StoryNode nextNode) {
        super(text, nextNode);
    }

    public LinkedTextChoice(String text, StoryNode nextNode, BooleanFunction<Player> condition) {
        super(text, nextNode, condition);
    }

    protected LinkedTextChoice(TextMessage message, BooleanFunction<Player> condition) {
        super(message, condition);
    }

    public static LinkedTextChoice fromSerialized(SerializedChoice serialized, GameStateDeserializer data) {
        TextMessage textMessage = data.getMessageMap().get(serialized.messageId());
        return new LinkedTextChoice(textMessage, serialized.condition());
    }

    @Override
    protected TextMessage createMessage() {
        return new TextMessage(getText(), true);
    }
}
