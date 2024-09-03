package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.serialize.SerializedChoiceTextInputNode;

import java.util.List;

public class ChoiceTextInputNode extends StoryTextNode {
    private final List<LinkedTextChoice> choices;
    private final TextMessage message;

    public ChoiceTextInputNode(String prompt, LinkedTextChoice... choices) {
        super(prompt);
        this.choices = List.of(choices);
        this.message = new TextMessage(this.getText(), false, true);
    }

    private ChoiceTextInputNode(TextMessage message, List<LinkedTextChoice> choices) {
        super(message.getText());
        this.choices = choices;
        this.message = message;
    }

    public static ChoiceTextInputNode fromSerialized(SerializedChoiceTextInputNode serialized, GameStateDeserializer data) {
        TextMessage textMessage = data.getMessageMap().get(serialized.messageId());
        List<LinkedTextChoice> choices = serialized.choices().stream()
                .map(choice -> {
                    LinkedTextChoice linkedTextChoice = LinkedTextChoice.fromSerialized(choice, data);
                    linkedTextChoice.then(data.getNode(choice.nextNodeId()));
                    return linkedTextChoice;
                })
                .toList();
        return new ChoiceTextInputNode(textMessage, choices);
    }

    public List<LinkedTextChoice> getChoices(Player player) {
        return choices.stream()
                .filter(choice -> choice.isAvailable(player))
                .toList();
    }

    public StoryNode getChoice(Player player, int index) {
        return getChoices(player).get(index).getNextNode();
    }

    public List<LinkedTextChoice> getAllChoices() {
        return choices;
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
