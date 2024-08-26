package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.node.Node;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class NodeHeader extends HBox {
    private final StackPane stackPane;
    private final NodeTitle nodeTitle;
    private NodeLink nextNodeLink;

    public NodeHeader(String defaultName, String nodeType) {
        getStyleClass().add("node-header");
        stackPane = new StackPane();
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        getChildren().add(stackPane);

        nodeTitle = new NodeTitle(defaultName, nodeType);
        stackPane.getChildren().add(nodeTitle);
        nextNodeLink = null;
    }

    public void addPreviousNodeLink() {
        NodeLink link = new NodeLink(ConnectionGender.FEMALE);
        StackPane.setAlignment(link, Pos.CENTER_LEFT);
        stackPane.getChildren().add(link);
    }

    public void addNextNodeLink() {
        nextNodeLink = new NodeLink(ConnectionGender.MALE);
        StackPane.setAlignment(nextNodeLink, Pos.CENTER_RIGHT);
        stackPane.getChildren().add(nextNodeLink);
    }

    public String getTitle() {
        return nodeTitle.getTitle();
    }

    public Node getNextNode() {
        Node next = null;
        if (nextNodeLink != null) {
            next = nextNodeLink.getParentNode();
        }
        return next;
    }
}
