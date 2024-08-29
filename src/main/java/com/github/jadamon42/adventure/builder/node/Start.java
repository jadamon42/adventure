package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionGender;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.node.StoryNode;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Start extends Node implements VisitableNode {
    private static final Start instance = new Start();
    private final StartHeader header;

    public Start() {
        getStyleClass().add("node");
        getChildren().add(new DraggableIcon());
        header = new StartHeader();
        getChildren().add(header);
    }

    @Override
    public String getTitle() {
        return "Start";
    }

    @Override
    public void setTitle(String title) {
        // Do nothing
    }

    @Override
    void sceneListener(ObservableValue<? extends Scene> obs, javafx.scene.Scene oldScene, javafx.scene.Scene newScene) {
        // Do nothing
    }

    public static Start getInstance() {
        return instance;
    }

    public StoryNode getAdventure() {
        StoryNode adventure = null;
        Node nextNode = header.getNextNode();
        if (nextNode instanceof StoryNodeTranslator adventureNode) {
            adventure = adventureNode.toStoryNode();
        }
        return adventure;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public static class StartHeader extends StackPane implements DraggableChild {
        private final NodeLink nextNodeLink;

        public StartHeader() {
            setPadding(new Insets(5));

            VBox vbox = new VBox();
            vbox.getStyleClass().add("node-title-container");
            Label nodeTitle = new Label();
            nodeTitle.getStyleClass().add("node-title");
            nodeTitle.setText("Start");
            nodeTitle.setPrefWidth(150);
            nodeTitle.setPrefHeight(50);
            vbox.getChildren().add(nodeTitle);
            getChildren().add(vbox);

            nextNodeLink = new NodeLink(ConnectionGender.MALE);
            StackPane.setAlignment(nextNodeLink, Pos.CENTER_RIGHT);
            getChildren().add(nextNodeLink);
        }

        @Override
        public void onParentDragged() {
            nextNodeLink.onParentDragged();
        }

        public Node getNextNode() {
            return nextNodeLink.getLinkedNode();
        }

        public String getNextConnectionId() {
            return nextNodeLink.getConnectionIds().isEmpty() ? null : nextNodeLink.getConnectionIds().getFirst();
        }

        public void setNextNodeConnection(ConnectionLine connectionLine) {
            nextNodeLink.addConnection(connectionLine);
        }
    }

    public String getNextConnectionId() {
        return header.getNextConnectionId();
    }

    public StartHeader getHeader() {
        return header;
    }
}
