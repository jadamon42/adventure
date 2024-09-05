package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.condition.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.common.node.LinkedTextChoice;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class ChoiceTextInputNode extends BasicNode implements StoryNodeTranslator, VisitableNode {
    private final Choices choices;

    public ChoiceTextInputNode() {
        NodeHeader header = new NodeHeader("Choice Text Input", "Choice Text Input Node");
        header.addPreviousNodeConnection();
        setHeader(header);
        setGameMessageInput("Enter choice prompt");
        choices = new Choices();
        setConditionals(choices);
    }

    public static String getDescription() {
        return """
                Present the player with a choice. Each choice can lead to a different story path.
                
                If a condition is supplied for one of the choices, \
                that choice will only be presented to the user if the condition is true.""";
    }

    @Override
    public com.github.jadamon42.adventure.common.node.ChoiceTextInputNode toStoryNode() {
        List<LinkedTextChoice> choices = new ArrayList<>();
        for (LinkedTextChoiceInput conditionInput : this.choices.getConditionalTextInputs()) {
            choices.add(conditionInput.toConditionalText());
        }
        return new com.github.jadamon42.adventure.common.node.ChoiceTextInputNode(getText(), choices.toArray(new LinkedTextChoice[0]));
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Choices getChoices() {
        return choices;
    }

    public void setChoice(
            int index,
            String promptText,
            String text,
            ConnectionLine nextConnection,
            ConnectionLine conditionConnection,
            boolean isDefault) {
        if (isDefault) {
            LinkedTextChoiceInput existingChoice = choices.getConditionalTextInputs().getLast();
            existingChoice.setPromptText(promptText);
            existingChoice.setText(text);
            existingChoice.setNextNodeConnection(nextConnection);
        } else if (index < choices.getConditionalTextInputs().size() - 1) {
            LinkedTextChoiceInput existingChoice = choices.getConditionalTextInputs().get(index);
            existingChoice.setPromptText(promptText);
            existingChoice.setText(text);
            existingChoice.setNextNodeConnection(nextConnection);
            existingChoice.setConditionConnection(conditionConnection);
        } else {
            LinkedTextChoiceInput newChoice = new LinkedTextChoiceInput(promptText);
            newChoice.setText(text);
            newChoice.setNextNodeConnection(nextConnection);
            newChoice.setConditionConnection(conditionConnection);
            choices.addNew(newChoice);
        }
    }

    public static class Choices extends ConditionalTextInputContainer<LinkedTextChoiceInput> {
        public Choices() {
            super();
            Label newLabel = new Label("+ New Choice");
            newLabel.getStyleClass().add("node-new-condition");
            newLabel.setOnMouseClicked(event -> addNew(new LinkedTextChoiceInput("")));
            LinkedTextChoiceInput condition1 = new LinkedTextChoiceInput("Choice 1");
            LinkedTextChoiceInput defaultCondition = new DefaultedLinkedTextChoiceInput("Default choice");
            addInitialInputs(condition1, defaultCondition, newLabel);
        }

        public String getConditionPrompt(int i) {
            return "Choice " + i;
        }
    }
}
