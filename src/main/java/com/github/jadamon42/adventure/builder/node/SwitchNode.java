package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeVisitor;
import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.builder.element.VisitableNode;
import com.github.jadamon42.adventure.builder.element.condition.ConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.condition.ConditionalTextInputContainer;
import com.github.jadamon42.adventure.builder.element.condition.DefaultedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.common.node.ConditionalText;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class SwitchNode extends BasicNode implements StoryNodeTranslator, VisitableNode {
    private final Cases cases;

    public SwitchNode() {
        NodeHeader header = new NodeHeader("Switch", "Switch Node");
        header.addPreviousNodeConnection();
        header.setNextNodeConnection();
        setHeader(header);
        cases = new Cases();
        setConditionals(cases);
    }

    public static String getDescription() {
        return "Display one of a set of messages to the user based on the supplied conditions.";
    }

    @Override
    public com.github.jadamon42.adventure.common.node.SwitchNode toStoryNode() {
        List<ConditionalText> cases = new ArrayList<>();
        for (ConditionalTextInput conditionInput : this.cases.getConditionalTextInputs()) {
            cases.add(conditionInput.toConditionalText());
        }
        var retval = new com.github.jadamon42.adventure.common.node.SwitchNode(cases.toArray(new ConditionalText[0]));
        Node nextNode = getNextNode();
        if (nextNode instanceof StoryNodeTranslator nextStoryNode) {
            retval.then(nextStoryNode.toStoryNode());
        }
        return retval;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Cases getCases() {
        return cases;
    }

    public void setCase(
            int index,
            String promptText,
            String text,
            ConnectionLine conditionConnection,
            boolean isDefault) {
        if (isDefault) {
            ConditionalTextInput existingBranch = cases.getConditionalTextInputs().getLast();
            existingBranch.setPromptText(promptText);
            existingBranch.setText(text);
        } else if (index < cases.getConditionalTextInputs().size() - 1) {
            ConditionalTextInput existingBranch = cases.getConditionalTextInputs().get(index);
            existingBranch.setPromptText(promptText);
            existingBranch.setText(text);
            existingBranch.setConditionConnection(conditionConnection);
        } else {
            ConditionalTextInput newBranch = new ConditionalTextInput(promptText);
            newBranch.setText(text);
            newBranch.setConditionConnection(conditionConnection);
            cases.addNew(newBranch);
        }
    }

    public static class Cases extends ConditionalTextInputContainer<ConditionalTextInput> {
        public Cases() {
            super();
            Label newLabel = new Label("+ New Case");
            newLabel.getStyleClass().add("node-new-condition");
            newLabel.setOnMouseClicked(event -> addNew(new ConditionalTextInput("")));
            ConditionalTextInput condition1 = new ConditionalTextInput("Case 1");
            ConditionalTextInput defaultCondition = new DefaultedConditionalTextInput("Default case");
            addInitialInputs(condition1, defaultCondition, newLabel);
        }

        public String getConditionPrompt(int i) {
            return "Case " + i;
        }
    }
}
