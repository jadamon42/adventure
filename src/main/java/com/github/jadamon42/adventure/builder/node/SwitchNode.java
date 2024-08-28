package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.builder.element.condition.ConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.condition.ConditionalTextInputContainer;
import com.github.jadamon42.adventure.builder.element.condition.DefaultedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.condition.LinkedConditionalTextInput;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.model.PlayerDelta;
import com.github.jadamon42.adventure.node.ConditionalText;
import com.github.jadamon42.adventure.node.LinkedConditionalText;
import com.github.jadamon42.adventure.node.StoryNode;
import com.github.jadamon42.adventure.util.PlayerDeltaBiFunction;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class SwitchNode extends BasicNode implements StoryNodeTranslator {
    private final Cases cases;

    public SwitchNode() {
        NodeHeader header = new NodeHeader("Switch", "Switch Node");
        header.addPreviousNodeLink();
        header.addNextNodeLink();
        setHeader(header);
        cases = new Cases();
        setConditionals(cases);
    }

    @Override
    public com.github.jadamon42.adventure.node.SwitchNode toStoryNode() {
        List<ConditionalText> cases = new ArrayList<>();
        for (ConditionalTextInput conditionInput : this.cases.getConditionalTextInputs()) {
            cases.add(conditionInput.toConditionalText());
        }
        var retval = new com.github.jadamon42.adventure.node.SwitchNode(cases.toArray(new ConditionalText[0]));
        Node nextNode = getNextNode();
        if (nextNode instanceof StoryNodeTranslator nextStoryNode) {
            retval.then(nextStoryNode.toStoryNode());
        }
        return retval;
    }

    private static class Cases extends ConditionalTextInputContainer<ConditionalTextInput> {
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
