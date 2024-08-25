package com.github.jadamon42.adventure.builder.element;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public abstract class ConditionalTextInputContainer extends VBox {
    public ConditionalTextInputContainer() {
        getStyleClass().add("node-option-container");
    }

    protected abstract String getConditionPrompt(int i);

    protected void reconfigureOptions() {
        int index = 1;
        for (Node child : getChildren()) {
            if (child instanceof ConditionalTextInput conditionalTextInput) {
                if (index == 1 && getChildren().size() == 3) {
                    conditionalTextInput.setDeletable(false);
                }
                conditionalTextInput.setPromptText(getConditionPrompt(index));
                index++;
            }
        }
    }

    protected void addNew(boolean linkable) {
        if (getChildren().getFirst() instanceof ConditionalTextInput condition1) {
            condition1.setDeletable(true);
            condition1.setOnDelete(event -> {
                getChildren().removeIf(child -> child == condition1);
                reconfigureOptions();
            });
        }
        ConditionalTextInput newCondition = new ConditionalTextInput(getConditionPrompt(getChildren().size() - 1), linkable);
        newCondition.setDeletable(true);
        newCondition.setOnDelete(event -> {
            getChildren().removeIf(child -> child == newCondition);
            reconfigureOptions();
        });
        getChildren().add(getChildren().size() - 2, newCondition);
    }
}
