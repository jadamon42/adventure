package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;

import java.util.List;

public class ConditionalTextNode extends LinkableTextNode {
    private final List<TextChoice> choices;

    /*
     * The first `TextChoice` that has a condition that returns true will be displayed.
     * The last `TextChoice` in the parameter list should not have a condition,
     * as it will be used as a default if no other conditions are met.
     */
    public ConditionalTextNode(TextChoice... options) {
        super(null);
        this.choices = List.of(options);
    }

    public TextChoice getChoice(Player player) {
        return choices.stream()
                      .filter(choice -> choice.isAvailable(player))
                      .findFirst()
                      .orElseThrow();
    }

    @Override
    public String getText() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Use getChoice(Player).getText() instead");
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
