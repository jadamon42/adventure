package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.ConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.ConditionalTextInputContainer;
import com.github.jadamon42.adventure.builder.element.DefaultedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import javafx.scene.control.Label;

public class SwitchNode extends BasicNode {
    public SwitchNode() {
        NodeHeader header = new NodeHeader("Switch", "Switch Node");
        header.addPreviousNodeLink();
        header.addNextNodeLink();
        setHeader(header);
        setConditionals(new Cases());
    }

    private static class Cases extends ConditionalTextInputContainer {
        public Cases() {
            super();
            Label newLabel = new Label("+ New Case");
            newLabel.getStyleClass().add("node-new-condition");
            newLabel.setOnMouseClicked(event -> addNew(false));
            ConditionalTextInput condition1 = new ConditionalTextInput("Case 1", false);
            getChildren().addAll(
                    condition1,
                    new DefaultedConditionalTextInput("Default case", false),
                    newLabel
            );
        }

        public String getConditionPrompt(int i) {
            return "Case " + i;
        }
    }
}
