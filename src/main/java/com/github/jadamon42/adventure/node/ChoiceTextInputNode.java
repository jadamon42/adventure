package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.StoryNodeVisitor;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.model.TextChoice;

import java.util.List;

public class ChoiceTextInputNode extends LinkableTextNode {
    private final List<TextChoice> choices;

    public ChoiceTextInputNode(String prompt, TextChoice... choices) {
        super(prompt);
        this.choices = List.of(choices);
    }

    public List<TextChoice> getChoices(Player player) {
        return choices.stream()
                .filter(choice -> choice.isAvailable(player))
                .toList();
    }

    public StoryNode getChoice(Player player, int index) {
        return getChoices(player).get(index).getNextNode();
    }

    @Override
    public StoryNode getNextNode() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Use getChoice(Player, int).getNextNode() instead");
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
