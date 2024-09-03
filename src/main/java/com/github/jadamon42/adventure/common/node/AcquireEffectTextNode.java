package com.github.jadamon42.adventure.common.node;

import com.github.jadamon42.adventure.common.model.Effect;
import com.github.jadamon42.adventure.common.model.TextMessage;
import com.github.jadamon42.adventure.common.state.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.common.state.serialize.SerializableAcquireEffectTextNode;

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

    public static AcquireEffectTextNode fromSerialized(SerializableAcquireEffectTextNode serialized, GameStateDeserializer data) {
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
