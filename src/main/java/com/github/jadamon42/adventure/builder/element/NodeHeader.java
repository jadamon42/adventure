package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.element.connection.ConnectionGender;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.node.Node;
import com.github.jadamon42.adventure.common.util.ListUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.util.List;

public class NodeHeader extends HBox implements InformableChild {
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

    public void addPreviousNodeConnection() {
        previousNodeLink = new NodeLink(ConnectionGender.FEMALE);
        StackPane.setAlignment(previousNodeLink, Pos.CENTER_LEFT);
        stackPane.getChildren().add(previousNodeLink);
    }

    public void addPreviousNodeConnection(ConnectionLine connectionLine) {
        if (previousNodeLink == null) {
            addPreviousNodeConnection();
        }
        previousNodeLink.addConnection(connectionLine);
    }

    public void setNextNodeConnection() {
        nextNodeLink = new NodeLink(ConnectionGender.MALE);
        StackPane.setAlignment(nextNodeLink, Pos.CENTER_RIGHT);
        stackPane.getChildren().add(nextNodeLink);
    }

    public void setNextNodeConnection(ConnectionLine connectionLine) {
        if (nextNodeLink == null) {
            setNextNodeConnection();
        }
        nextNodeLink.addConnection(connectionLine);
    }

    public void setTitle(String title) {
        nodeTitle.setTitle(title);
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

    public String getNextNodeConnectionId() {
        return ListUtils.getFirst(nextNodeLink.getConnectionIds());
    }

    public List<String> getPreviousNodeConnectionIds() {
        return previousNodeLink.getConnectionIds();
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

    @Override
    public void onParentDeleted() {
        if (previousNodeLink != null) {
            previousNodeLink.onParentDeleted();
        }
        if (nextNodeLink != null) {
            nextNodeLink.onParentDeleted();
        }
    }

    public void setChildOnKeyTyped(EventHandler<? super KeyEvent> eventHandler) {
        nodeTitle.setChildOnKeyTyped(eventHandler);
    }
}
