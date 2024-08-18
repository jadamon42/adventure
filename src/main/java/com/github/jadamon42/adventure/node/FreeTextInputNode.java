package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.model.PlayerDelta;
import com.github.jadamon42.adventure.util.SerializableBiFunction;


public class FreeTextInputNode extends LinkableStoryTextNode {

    private final SerializableBiFunction<Player, Object, PlayerDelta> textConsumer;

    public FreeTextInputNode(String prompt, SerializableBiFunction<Player, Object, PlayerDelta> consumer) {
        super(prompt);
        this.textConsumer = consumer;
    }

    public SerializableBiFunction<Player, Object, PlayerDelta> getTextConsumer() {
        return textConsumer;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
