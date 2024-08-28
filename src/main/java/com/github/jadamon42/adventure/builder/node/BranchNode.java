package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.condition.ConditionalTextInputContainer;
import com.github.jadamon42.adventure.builder.element.condition.DefaultedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.condition.DefaultedLinkedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.condition.LinkedConditionalTextInput;
import com.github.jadamon42.adventure.node.LinkedConditionalText;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class BranchNode extends BasicNode implements StoryNodeTranslator {
    private final Branches branches;

    public BranchNode() {
        NodeHeader header = new NodeHeader("Branch", "Branch Node");
        header.addPreviousNodeLink();
        setHeader(header);
        branches = new Branches();
        setConditionals(branches);
    }

    @Override
    public com.github.jadamon42.adventure.node.BranchNode toStoryNode() {
        List<LinkedConditionalText> branches = new ArrayList<>();
        for (LinkedConditionalTextInput conditionInput : this.branches.getConditionalTextInputs()) {
            branches.add(conditionInput.toConditionalText());
        }
        return new com.github.jadamon42.adventure.node.BranchNode(branches.toArray(new LinkedConditionalText[0]));
    }

    private static class Branches extends ConditionalTextInputContainer<LinkedConditionalTextInput> {
        public Branches() {
            super();
            Label newLabel = new Label("+ New Branch");
            newLabel.getStyleClass().add("node-new-condition");
            newLabel.setOnMouseClicked(event -> addNew(new LinkedConditionalTextInput("")));
            LinkedConditionalTextInput condition1 = new LinkedConditionalTextInput("Branch 1");
            LinkedConditionalTextInput defaultCondition = new DefaultedLinkedConditionalTextInput("Default Option");
            addInitialInputs(condition1, defaultCondition, newLabel);
        }

        public String getConditionPrompt(int i) {
            return "Branch " + i;
        }
    }
}
