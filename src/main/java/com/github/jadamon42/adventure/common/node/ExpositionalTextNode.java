package com.github.jadamon42.adventure.common.node;

import com.github.jadamon42.adventure.common.model.TextMessage;
import com.github.jadamon42.adventure.common.state.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.common.state.serialize.SerializableExpositionalTextNode;

import java.util.UUID;

public class ExpositionalTextNode extends LinkableStoryTextNode {
    private final TextMessage message;

    public ExpositionalTextNode(String text) {
        super(text);
        this.message = new TextMessage(this.getText(), false);
    }

    private ExpositionalTextNode(UUID id, TextMessage message) {
        super(id, message.getText());
        this.message = message;
    }

    public static ExpositionalTextNode fromSerialized(SerializableExpositionalTextNode serialized, GameStateDeserializer data) {
        TextMessage message = data.getMessageMap().get(serialized.messageId());
        return new ExpositionalTextNode(serialized.id(), message);
    }

    @Override
    public TextMessage getMessage() {
        return message;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
