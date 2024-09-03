package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.serialize.SerializedChoice;
import com.github.jadamon42.adventure.util.BooleanFunction;

public class LinkedConditionalText extends ConditionalText implements Linkable {
    private StoryNode nextNode;

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

    protected LinkedConditionalText(TextMessage message, BooleanFunction<Player> condition) {
        super(message, condition);
        this.nextNode = null;
    }

    public static LinkedConditionalText fromSerialized(SerializedChoice serialized, GameStateDeserializer data) {
        TextMessage textMessage = data.getMessageMap().get(serialized.messageId());
        return new LinkedConditionalText(textMessage, serialized.condition());
    }

    @Override
    public StoryNode getNextNode() {
        return nextNode;
    }

    @Override
    public <T extends StoryNode> T then(T nextNode) {
        this.nextNode = nextNode;
        return nextNode;
    }
}
