package com.github.jadamon42.adventure.builder.element;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class NodeTitle extends VBox {
    private final TextField nodeTitleTextField;

    public NodeTitle(String defaultTitle, String nodeType) {
        getStyleClass().add("node-title-container");
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

    public String getTitle() {
        return nodeTitleTextField.getText();
    }
}
