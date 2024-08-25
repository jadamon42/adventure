package com.github.jadamon42.adventure.builder.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class NodeFooter extends HBox {
    private final VBox left;
    private final VBox right;

    public NodeFooter() {
        getStyleClass().add("node-footer");
        left = new VBox();
        left.setAlignment(Pos.BOTTOM_LEFT);
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        right = new VBox();
        right.setAlignment(Pos.BOTTOM_RIGHT);
        getChildren().addAll(left, spacer, right);
    }

    public void addAttachment(String text) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(5);
        FontAwesomeIconView itemConnectorIcon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE_THIN);
        itemConnectorIcon.setFill(Paint.valueOf("lightgrey"));
        itemConnectorIcon.setGlyphSize(12);
        Label label = new Label();
        label.setText(text);
        label.setTextFill(Paint.valueOf("lightgrey"));
        hbox.getChildren().addAll(itemConnectorIcon, label);
        left.getChildren().add(hbox);
    }

    public void addAttacher() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setSpacing(5);
        FontAwesomeIconView itemConnectorIcon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE_THIN);
        itemConnectorIcon.setFill(Paint.valueOf("lightgrey"));
        itemConnectorIcon.setGlyphSize(12);
        Label label = new Label();
        label.setText("Attach");
        label.setTextFill(Paint.valueOf("lightgrey"));
        hbox.getChildren().addAll(label, itemConnectorIcon);
        right.getChildren().add(hbox);
    }
}
