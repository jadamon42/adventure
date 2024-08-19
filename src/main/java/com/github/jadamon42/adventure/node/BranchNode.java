package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;

import java.util.List;

public class BranchNode extends StoryNode {
    private final List<LinkedConditionalText> choices;

    /*
     * The first `TextChoice` that has a condition that returns true will be displayed.
     * The last `TextChoice` in the parameter list should not have a condition,
     * as it will be used as a default if no other conditions are met.
     */
    public BranchNode(LinkedConditionalText... options) {
        super();
        this.choices = List.of(options);
    }

    public LinkedConditionalText getChoice(Player player) {
        return choices.stream()
                .filter(choice -> choice.isAvailable(player))
                .findFirst()
                .orElseThrow();
    }

    public List<LinkedConditionalText> getAllChoices() {
        return choices;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
