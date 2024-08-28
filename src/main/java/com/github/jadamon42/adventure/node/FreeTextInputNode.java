package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.PlayerDeltaBiFunction;


public class FreeTextInputNode extends LinkableStoryTextNode {

    private final PlayerDeltaBiFunction<Player, Object> textConsumer;
    private final TextMessage message;

    public FreeTextInputNode(String prompt, PlayerDeltaBiFunction<Player, Object> consumer) {
        super(prompt);
        this.textConsumer = consumer;
        this.message = new TextMessage(this.getText(), false, true);
    }

    public PlayerDeltaBiFunction<Player, Object> getTextConsumer() {
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
