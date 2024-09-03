package com.github.jadamon42.adventure.common.node;

import com.github.jadamon42.adventure.common.model.TextMessage;
import com.github.jadamon42.adventure.common.state.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.common.state.serialize.SerializableExpositionalTextNode;

public class ExpositionalTextNode extends LinkableStoryTextNode {
    private final TextMessage message;

    public ExpositionalTextNode(String text) {
        super(text);
        this.message = new TextMessage(this.getText(), false);
    }

    private ExpositionalTextNode(TextMessage message) {
        super(message.getText());
        this.message = message;
    }

    public static ExpositionalTextNode fromSerialized(SerializableExpositionalTextNode serialized, GameStateDeserializer data) {
        TextMessage message = data.getMessageMap().get(serialized.messageId());
        return new ExpositionalTextNode(message);
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
