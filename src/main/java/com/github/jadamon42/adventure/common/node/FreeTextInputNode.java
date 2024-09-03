package com.github.jadamon42.adventure.common.node;

import com.github.jadamon42.adventure.common.model.TextMessage;
import com.github.jadamon42.adventure.common.state.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.common.state.serialize.SerializableFreeTextInputNode;
import com.github.jadamon42.adventure.common.util.PlayerDeltaBiFunction;


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

    public static FreeTextInputNode fromSerialized(SerializableFreeTextInputNode serialized, GameStateDeserializer data) {
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
