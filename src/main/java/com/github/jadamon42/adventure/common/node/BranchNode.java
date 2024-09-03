package com.github.jadamon42.adventure.common.node;

import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.state.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.common.state.serialize.SerializableBranchNode;

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

    public static BranchNode fromSerialized(SerializableBranchNode serialized, GameStateDeserializer data) {
        LinkedConditionalText[] choices = serialized.choices().stream()
                .map((serializedChoice) -> {
                    LinkedConditionalText condition = LinkedConditionalText.fromSerialized(serializedChoice, data);
                    condition.then(data.getNode(serializedChoice.nextNodeId()));
                    return condition;
                })
                .toArray(LinkedConditionalText[]::new);
        return new BranchNode(choices);
    }

    public LinkedConditionalText getBranch(Player player) {
        return choices.stream()
                .filter(choice -> choice.isAvailable(player))
                .findFirst()
                .orElseThrow();
    }

    public List<LinkedConditionalText> getAllBranches() {
        return choices;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
