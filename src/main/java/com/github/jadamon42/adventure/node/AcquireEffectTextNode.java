package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.StoryNodeVisitor;
import com.github.jadamon42.adventure.model.Effect;

public class AcquireEffectTextNode extends LinkableTextNode {
    private final Effect effect;

    public AcquireEffectTextNode(String text, Effect effect) {
        super(text);
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
