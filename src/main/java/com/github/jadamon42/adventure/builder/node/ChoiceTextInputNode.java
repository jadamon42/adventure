package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.ConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.ConditionalTextInputContainer;
import com.github.jadamon42.adventure.builder.element.DefaultedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import javafx.scene.control.Label;

public class ChoiceTextInputNode extends BasicNode {
    public ChoiceTextInputNode() {
        NodeHeader header = new NodeHeader("Choice Text Input", "Choice Text Input Node");
        header.addPreviousNodeLink();
        setHeader(header);
        setGameMessageInput("Enter choice prompt");
        setConditionals(new Choices());
    }

    private static class Choices extends ConditionalTextInputContainer {
        public Choices() {
            super();
            Label newLabel = new Label("+ New Choice");
            newLabel.getStyleClass().add("node-new-condition");
            newLabel.setOnMouseClicked(event -> addNew(true));
            ConditionalTextInput condition1 = new ConditionalTextInput("Choice 1", true);
            getChildren().addAll(
                    condition1,
                    new DefaultedConditionalTextInput("Default choice", true),
                    newLabel
            );
        }

        public String getConditionPrompt(int i) {
            return "Choice " + i;
        }
    }
}
