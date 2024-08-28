package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.node.StoryNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Start extends Node {
    private static final Start instance = new Start();
    private final StartHeader header;

    public Start() {
        getStyleClass().add("node");
        getChildren().add(new DraggableIcon());
        header = new StartHeader();
        getChildren().add(header);
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

    private static class StartHeader extends StackPane implements DraggableChild {
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
    }
}
