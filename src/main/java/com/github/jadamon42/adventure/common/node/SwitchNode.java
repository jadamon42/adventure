package com.github.jadamon42.adventure.common.node;

import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.state.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.common.state.serialize.SerializableSwitchNode;

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

    public static SwitchNode fromSerialized(SerializableSwitchNode serialized, GameStateDeserializer data) {
        ConditionalText[] choices = serialized.choices().stream()
                .map((serializedChoice) -> ConditionalText.fromSerialized(serializedChoice, data))
                .toArray(ConditionalText[]::new);
        return new SwitchNode(choices);
    }

    public ConditionalText getCase(Player player) {
        return choices.stream()
                      .filter(choice -> choice.isAvailable(player))
                      .findFirst()
                      .orElseThrow();
    }

    public List<ConditionalText> getAllCases() {
        return choices;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
