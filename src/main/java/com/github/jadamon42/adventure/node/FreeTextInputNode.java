package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.serialize.SerializedFreeTextInputNode;
import com.github.jadamon42.adventure.util.PlayerDeltaBiFunction;


public class FreeTextInputNode extends LinkableStoryTextNode {

    private final PlayerDeltaBiFunction<Object> textConsumer;
    private final TextMessage message;

    public FreeTextInputNode(String prompt, PlayerDeltaBiFunction<Object> consumer) {
        super(prompt);
        this.textConsumer = consumer;
        this.message = new TextMessage(this.getText(), false, true);
    }

    private FreeTextInputNode(TextMessage message, PlayerDeltaBiFunction<Object> consumer) {
        super(message.getText());
        this.textConsumer = consumer;
        this.message = message;
    }

    public static FreeTextInputNode fromSerialized(SerializedFreeTextInputNode serialized, GameStateDeserializer data) {
        TextMessage message = data.getMessageMap().get(serialized.messageId());
        return new FreeTextInputNode(message, serialized.consumer());
    }

    public PlayerDeltaBiFunction<Object> getTextConsumer() {
        return textConsumer;
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
