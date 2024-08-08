package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;

import java.util.List;

public class ChoiceTextInputNode extends StoryTextNode {
    private final List<LinkedTextChoice> choices;

    public ChoiceTextInputNode(String prompt, LinkedTextChoice... choices) {
        super(prompt);
        this.choices = List.of(choices);
    }

    public List<LinkedTextChoice> getChoices(Player player) {
        return choices.stream()
                .filter(choice -> choice.isAvailable(player))
                .toList();
    }

    public StoryNode getChoice(Player player, int index) {
        return getChoices(player).get(index).getNextNode();
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
