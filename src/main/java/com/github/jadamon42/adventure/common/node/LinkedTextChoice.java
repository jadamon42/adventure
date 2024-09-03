package com.github.jadamon42.adventure.common.node;

import com.github.jadamon42.adventure.common.model.TextMessage;
import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.state.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.common.state.serialize.SerializableConditional;
import com.github.jadamon42.adventure.common.util.BooleanFunction;

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

    public static LinkedTextChoice fromSerialized(SerializableConditional serialized, GameStateDeserializer data) {
        TextMessage textMessage = data.getMessageMap().get(serialized.messageId());
        return new LinkedTextChoice(textMessage, serialized.condition());
    }

    @Override
    protected TextMessage createMessage() {
        return new TextMessage(getText(), true);
    }
}
