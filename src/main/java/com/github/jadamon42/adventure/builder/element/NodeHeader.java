package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.element.connection.ConnectionGender;
import com.github.jadamon42.adventure.builder.node.Node;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class NodeHeader extends HBox implements DraggableChild {
    private final StackPane stackPane;
    private final NodeTitle nodeTitle;
    private NodeLink previousNodeLink;
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
        previousNodeLink = new NodeLink(ConnectionGender.FEMALE);
        StackPane.setAlignment(previousNodeLink, Pos.CENTER_LEFT);
        stackPane.getChildren().add(previousNodeLink);
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
            next = nextNodeLink.getLinkedNode();
        }
        return next;
    }

    @Override
    public void onParentDragged() {
        if (previousNodeLink != null) {
            previousNodeLink.onParentDragged();
        }
        if (nextNodeLink != null) {
            nextNodeLink.onParentDragged();
        }
    }

    public void setChildOnKeyTyped(EventHandler<? super KeyEvent> eventHandler) {
        nodeTitle.setChildOnKeyTyped(eventHandler);
    }
}
