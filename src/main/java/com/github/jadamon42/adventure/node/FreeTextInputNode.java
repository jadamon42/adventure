package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.SerializableBiConsumer;


public class FreeTextInputNode extends LinkableStoryTextNode {

    private final SerializableBiConsumer<Player, Object> textConsumer;

    public FreeTextInputNode(String prompt, SerializableBiConsumer<Player, Object> consumer) {
        super(prompt);
        this.textConsumer = consumer;
    }

    public SerializableBiConsumer<Player, Object> getTextConsumer() {
        return textConsumer;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
