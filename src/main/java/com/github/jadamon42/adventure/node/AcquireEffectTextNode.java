package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Effect;
import com.github.jadamon42.adventure.model.TextMessage;

public class AcquireEffectTextNode extends LinkableStoryTextNode {
    private final Effect effect;
    private final TextMessage message;

    public AcquireEffectTextNode(String text, Effect effect) {
        super(text);
        this.effect = effect;
        this.message = new TextMessage(this.getText(), false);
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
