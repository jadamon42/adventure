package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.ConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.ConditionalTextInputContainer;
import com.github.jadamon42.adventure.builder.element.DefaultedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import javafx.scene.control.Label;

public class BranchNode extends BasicNode {
    public BranchNode() {
        NodeHeader header = new NodeHeader("Branch", "Branch Node");
        header.addPreviousNodeLink();
        setHeader(header);
        setConditionals(new Branches());
    }

    private static class Branches extends ConditionalTextInputContainer {
        public Branches() {
            super();
            Label newLabel = new Label("+ New Branch");
            newLabel.getStyleClass().add("node-new-condition");
            newLabel.setOnMouseClicked(event -> addNew(true));
            ConditionalTextInput condition1 = new ConditionalTextInput("Branch 1", true);
            getChildren().addAll(
                    condition1,
                    new DefaultedConditionalTextInput("Default option", true),
                    newLabel
            );
        }

        public String getConditionPrompt(int i) {
            return "Branch " + i;
        }
    }
}
