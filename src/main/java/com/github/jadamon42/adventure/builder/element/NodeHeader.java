package com.github.jadamon42.adventure.builder.element;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class NodeHeader extends HBox {
    private final StackPane stackPane;

    public NodeHeader(String defaultName, String nodeType) {
        getStyleClass().add("node-header");
        stackPane = new StackPane();
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        getChildren().add(stackPane);

        stackPane.getChildren().add(new NodeTitle(defaultName, nodeType));
    }

    public void addPreviousNodeLink() {
        NodeLink link = new NodeLink(ConnectionGender.FEMALE);
        StackPane.setAlignment(link, Pos.CENTER_LEFT);
        stackPane.getChildren().add(link);
    }

    public void addNextNodeLink() {
        NodeLink link = new NodeLink(ConnectionGender.MALE);
        StackPane.setAlignment(link, Pos.CENTER_RIGHT);
        stackPane.getChildren().add(link);
    }
}
