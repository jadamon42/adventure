package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.LinkableTextChoice;
import com.github.jadamon42.adventure.model.Player;

import java.util.List;

public class BranchTextNode extends LinkableTextNode {
    private final List<LinkableTextChoice> choices;

    /*
     * The first `TextChoice` that has a condition that returns true will be displayed.
     * The last `TextChoice` in the parameter list should not have a condition,
     * as it will be used as a default if no other conditions are met.
     */
    public BranchTextNode(LinkableTextChoice... options) {
        super(null);
        this.choices = List.of(options);
    }

    public LinkableTextChoice getChoice(Player player) {
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
    public StoryNode getNextNode() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Use getChoice(Player).getNextNode() instead");
    }


    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
