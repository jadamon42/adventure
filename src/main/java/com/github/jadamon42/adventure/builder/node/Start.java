package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.DraggableIcon;
import com.github.jadamon42.adventure.builder.element.NodeLink;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Start extends Node {
    public Start() {
        getStyleClass().add("node");
        getChildren().add(new DraggableIcon());

        HBox hbox = new HBox();
        hbox.getStyleClass().add("node-header");
        StackPane stackPane = new StackPane();
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        hbox.getChildren().add(stackPane);

        VBox vbox = new VBox();
        vbox.getStyleClass().add("node-title-container");
        Label nodeTitle = new Label();
        nodeTitle.getStyleClass().add("node-title");
        nodeTitle.setText("Start");
        nodeTitle.setPrefWidth(150);
        nodeTitle.setPrefHeight(50);
        vbox.getChildren().add(nodeTitle);
        stackPane.getChildren().add(vbox);

        NodeLink link = new NodeLink();
        StackPane.setAlignment(link, Pos.CENTER_RIGHT);
        stackPane.getChildren().add(link);

        getChildren().add(stackPane);
//        NodeHeader header = new NodeHeader("Start", "");
//        header.addNextNodeLink();
//        setHeader(header);
    }
}
