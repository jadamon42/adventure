package com.github.jadamon42.adventure.builder.element;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class NodeTitle extends VBox {
    public NodeTitle(String defaultTitle, String nodeType) {
        getStyleClass().add("node-title-container");
        TextField nodeTitle = new TextField();
        nodeTitle.getStyleClass().add("node-title");
        nodeTitle.setText(defaultTitle);
        nodeTitle.setFocusTraversable(false);
        nodeTitle.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
                getScene().getRoot().requestFocus();
            }
        });
        Label nodeTypeLabel = new Label();
        nodeTypeLabel.getStyleClass().add("node-type");
        nodeTypeLabel.setText(nodeType);
        getChildren().addAll(nodeTitle, nodeTypeLabel);
    }
}
