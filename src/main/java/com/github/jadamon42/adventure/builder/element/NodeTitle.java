package com.github.jadamon42.adventure.builder.element;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class NodeTitle extends VBox {
    private final TextField nodeTitleTextField;
    private EventHandler<? super KeyEvent> eventHandler;

    public NodeTitle(String defaultTitle, String nodeType) {
        getStyleClass().add("node-title-container");
        eventHandler = event -> {};
        nodeTitleTextField = new TextField();
        nodeTitleTextField.getStyleClass().add("node-title");
        nodeTitleTextField.setText(defaultTitle);
        nodeTitleTextField.setFocusTraversable(false);
        nodeTitleTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
                getScene().getRoot().requestFocus();
            }
        });
        Label nodeTypeLabel = new Label();
        nodeTypeLabel.getStyleClass().add("node-type");
        nodeTypeLabel.setText(nodeType);
        getChildren().addAll(nodeTitleTextField, nodeTypeLabel);
    }

    public void setTitle(String title) {
        nodeTitleTextField.setText(title);
        eventHandler.handle(new KeyEvent(KeyEvent.KEY_TYPED, "", "", KeyCode.UNDEFINED, false, false, false, false));
    }

    public String getTitle() {
        return nodeTitleTextField.getText();
    }

    public void setChildOnKeyTyped(EventHandler<? super KeyEvent> eventHandler) {
        this.eventHandler = eventHandler;
        nodeTitleTextField.setOnKeyTyped(eventHandler);
    }
}
