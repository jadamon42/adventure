package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.condition.ConditionalTextInputContainer;
import com.github.jadamon42.adventure.builder.element.condition.DefaultedLinkedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.condition.LinkedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.common.node.LinkedConditionalText;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class BranchNode extends BasicNode implements StoryNodeTranslator, VisitableNode {
    private final Branches branches;
    private com.github.jadamon42.adventure.common.node.BranchNode storyNode;

    public BranchNode() {
        NodeHeader header = new NodeHeader("Branch", "Branch Node");
        header.addPreviousNodeConnection();
        setHeader(header);
        branches = new Branches();
        setConditionals(branches);
    }

    public static String getDescription() {
        return "Advance the story along the branch with the first condition that is true.";
    }

    @Override
    public com.github.jadamon42.adventure.common.node.BranchNode toStoryNode() {
        if (storyNode == null) {
            storyNode = buildStoryNode();
        }
        return storyNode;
    }

    @Override
    public void clearCachedStoryNode() {
        storyNode = null;
    }

    private com.github.jadamon42.adventure.common.node.BranchNode buildStoryNode() {
        List<LinkedConditionalText> branches = new ArrayList<>();
        for (LinkedConditionalTextInput conditionInput : this.branches.getConditionalTextInputs()) {
            branches.add(conditionInput.toConditionalText());
        }
        return new com.github.jadamon42.adventure.common.node.BranchNode(branches.toArray(new LinkedConditionalText[0]));
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Branches getBranches() {
        return branches;
    }

    public void setBranch(
            int index,
            String promptText,
            String text,
            ConnectionLine nextConnection,
            ConnectionLine conditionConnection,
            boolean isDefault) {
        if (isDefault) {
            LinkedConditionalTextInput existingBranch = branches.getConditionalTextInputs().getLast();
            existingBranch.setPromptText(promptText);
            existingBranch.setText(text);
            existingBranch.setNextNodeConnection(nextConnection);
        } else if (index < branches.getConditionalTextInputs().size() - 1) {
            LinkedConditionalTextInput existingBranch = branches.getConditionalTextInputs().get(index);
            existingBranch.setPromptText(promptText);
            existingBranch.setText(text);
            existingBranch.setNextNodeConnection(nextConnection);
            existingBranch.setConditionConnection(conditionConnection);
        } else {
            LinkedConditionalTextInput newBranch = new LinkedConditionalTextInput(promptText);
            newBranch.setText(text);
            newBranch.setNextNodeConnection(nextConnection);
            newBranch.setConditionConnection(conditionConnection);
            branches.addNew(newBranch);
        }
    }

    public static class Branches extends ConditionalTextInputContainer<LinkedConditionalTextInput> {
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
