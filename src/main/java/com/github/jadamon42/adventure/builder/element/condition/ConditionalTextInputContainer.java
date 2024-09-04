package com.github.jadamon42.adventure.builder.element.condition;

import com.github.jadamon42.adventure.builder.element.InformableChild;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ConditionalTextInputContainer<T extends AbstractConditionalTextInput> extends VBox implements Iterable<T>, InformableChild {
    private final List<T> conditionalTextInputs;

    public ConditionalTextInputContainer() {
        getStyleClass().add("node-option-container");
        conditionalTextInputs = new ArrayList<>();
    }

    protected abstract String getConditionPrompt(int i);

    public List<T> getConditionalTextInputs() {
        return conditionalTextInputs;
    }

    protected void addInitialInputs(T condition1, T defaultCondition, Label newLabel) {
        conditionalTextInputs.add(condition1);
        conditionalTextInputs.add(defaultCondition);
        getChildren().addAll(
                condition1,
                defaultCondition,
                newLabel
        );
    }

    protected void reconfigureOptions() {
        int index = 1;
        for (Node child : getChildren()) {
            if (child instanceof AbstractConditionalTextInput conditionalTextInput) {
                if (index == 1 && getChildren().size() == 3) {
                    conditionalTextInput.setDeletable(false);
                }
                conditionalTextInput.setPromptText(getConditionPrompt(index));
                index++;
            }
        }
    }

    public void addNew(T newCondition) {
        if (getChildren().getFirst() instanceof AbstractConditionalTextInput condition1) {
            condition1.setDeletable(true);
            condition1.setOnDelete(event -> {
                getChildren().removeIf(child -> child == condition1);
                reconfigureOptions();
            });
        }
        newCondition.setPromptText(getConditionPrompt(getChildren().size() - 1));
        newCondition.setDeletable(true);
        newCondition.setOnDelete(event -> {
            getChildren().removeIf(child -> child == newCondition);
            reconfigureOptions();
        });
        conditionalTextInputs.add(conditionalTextInputs.size() - 1, newCondition);
        getChildren().add(getChildren().size() - 2, newCondition);
    }

    @Override
    public Iterator<T> iterator() {
        return conditionalTextInputs.iterator();
    }

    @Override
    public void onParentDragged() {
        for (T condition : conditionalTextInputs) {
            condition.onParentDragged();
        }
    }

    @Override
    public void onParentDeleted() {
        for (T condition : conditionalTextInputs) {
            condition.onParentDeleted();
        }
    }
}
