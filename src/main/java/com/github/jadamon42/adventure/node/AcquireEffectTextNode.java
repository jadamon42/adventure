package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Effect;
import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.serialize.SerializedAcquireEffectTextNode;

public class AcquireEffectTextNode extends LinkableStoryTextNode {
    private final Effect effect;
    private final TextMessage message;

    public AcquireEffectTextNode(String text, Effect effect) {
        super(text);
        this.effect = effect;
        this.message = new TextMessage(this.getText(), false);
    }

    private AcquireEffectTextNode(TextMessage message, Effect effect) {
        super(message.getText());
        this.effect = effect;
        this.message = message;
    }

    public static AcquireEffectTextNode fromSerialized(SerializedAcquireEffectTextNode serialized, GameStateDeserializer data) {
        TextMessage textMessage = data.getMessageMap().get(serialized.messageId());
        Effect effect = data.getEffectMap().get(serialized.effectId());
        return new AcquireEffectTextNode(textMessage, effect);
    }

    public Effect getEffect() {
        return effect;
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
