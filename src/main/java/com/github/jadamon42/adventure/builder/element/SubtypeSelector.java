package com.github.jadamon42.adventure.builder.element;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;

public class SubtypeSelector extends HBox {
    private final ComboBox<String> comboBox = new ComboBox<>();
    private final Map<String, EventHandler<ActionEvent>> eventHandlers = new HashMap<>();

    public SubtypeSelector(String defaultLabel, EventHandler<ActionEvent> defaultEventHandler) {
        getStyleClass().add("node-subtype-selector-container");
        comboBox.getStyleClass().add("node-subtype-selector");
        comboBox.setOnAction(event -> handleSelectionChange());
        getChildren().add(comboBox);
        addOption(defaultLabel, defaultEventHandler);
        comboBox.setValue(defaultLabel);
        handleSelectionChange();
    }

    public void addOption(String label, EventHandler<ActionEvent> eventHandler) {
        comboBox.getItems().add(label);
        eventHandlers.put(label, eventHandler);
    }

    private void handleSelectionChange() {
        String selected = comboBox.getValue();
        EventHandler<ActionEvent> handler = eventHandlers.get(selected);
        if (handler != null) {
            handler.handle(new ActionEvent(comboBox, null));
        }
    }

    public String getSelectedOption() {
        return comboBox.getValue();
    }

    public void setSelectedOption(String option) {
        comboBox.setValue(option);
        handleSelectionChange();
    }
}
