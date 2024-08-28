package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.condition.*;
import com.github.jadamon42.adventure.node.LinkedTextChoice;
import com.github.jadamon42.adventure.node.StoryNode;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class ChoiceTextInputNode extends BasicNode implements StoryNodeTranslator {
    private final Choices choices;

    public ChoiceTextInputNode() {
        NodeHeader header = new NodeHeader("Choice Text Input", "Choice Text Input Node");
        header.addPreviousNodeLink();
        setHeader(header);
        setGameMessageInput("Enter choice prompt");
        choices = new Choices();
        setConditionals(choices);
    }

    @Override
    public com.github.jadamon42.adventure.node.ChoiceTextInputNode toStoryNode() {
        List<LinkedTextChoice> choices = new ArrayList<>();
        for (LinkedTextChoiceInput conditionInput : this.choices.getConditionalTextInputs()) {
            choices.add(conditionInput.toConditionalText());
        }
        return new com.github.jadamon42.adventure.node.ChoiceTextInputNode(getText(), choices.toArray(new LinkedTextChoice[0]));
    }

    private static class Choices extends ConditionalTextInputContainer<LinkedTextChoiceInput> {
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
