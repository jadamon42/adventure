package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.serialize.SerializedSwitchNode;

import java.util.List;

public class SwitchNode extends LinkableStoryNode {
    private final List<ConditionalText> choices;

    /*
     * The first `TextChoice` that has a condition that returns true will be displayed.
     * The last `TextChoice` in the parameter list should not have a condition,
     * as it will be used as a default if no other conditions are met.
     */
    public SwitchNode(ConditionalText... options) {
        super();
        this.choices = List.of(options);
    }

    public static SwitchNode fromSerialized(SerializedSwitchNode serialized, GameStateDeserializer data) {
        ConditionalText[] choices = serialized.choices().stream()
                .map((serializedChoice) -> ConditionalText.fromSerialized(serializedChoice, data))
                .toArray(ConditionalText[]::new);
        return new SwitchNode(choices);
    }

    public ConditionalText getChoice(Player player) {
        return choices.stream()
                      .filter(choice -> choice.isAvailable(player))
                      .findFirst()
                      .orElseThrow();
    }

    public List<ConditionalText> getAllChoices() {
        return choices;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
