package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.serialize.SerializedExpositionalTextNode;

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

    public static ExpositionalTextNode fromSerialized(SerializedExpositionalTextNode serialized, GameStateDeserializer data) {
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
