package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;

import java.util.List;

public class SwitchNode extends LinkableStoryNode {
    private final List<TextChoice> choices;

    /*
     * The first `TextChoice` that has a condition that returns true will be displayed.
     * The last `TextChoice` in the parameter list should not have a condition,
     * as it will be used as a default if no other conditions are met.
     */
    public SwitchNode(TextChoice... options) {
        super();
        this.choices = List.of(options);
    }

    public TextChoice getChoice(Player player) {
        return choices.stream()
                      .filter(choice -> choice.isAvailable(player))
                      .findFirst()
                      .orElseThrow();
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
