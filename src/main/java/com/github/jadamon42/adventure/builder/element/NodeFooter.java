package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.node.Node;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class NodeFooter extends HBox implements DraggableChild {
    private final VBox left;
    private final List<AttachmentLink> leftAttachments;
    private final VBox right;
    private final List<AttachmentLink> rightAttachments;

    public NodeFooter() {
        getStyleClass().add("node-footer");
        left = new VBox();
        left.setAlignment(Pos.BOTTOM_LEFT);
        leftAttachments = new ArrayList<>();
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        right = new VBox();
        right.setAlignment(Pos.BOTTOM_RIGHT);
        rightAttachments = new ArrayList<>();
        getChildren().addAll(left, spacer, right);
    }

    public void addAttachment(String text, ConnectionType connectionType) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(5);
        AttachmentLink attachmentLink = new AttachmentLink(connectionType, ConnectionGender.FEMALE);
        Label label = new Label();
        label.setText(text);
        label.setTextFill(Paint.valueOf("lightgrey"));
        hbox.getChildren().addAll(attachmentLink, label);
        left.getChildren().add(hbox);
        leftAttachments.add(attachmentLink);
    }

    public void addAttacher(ConnectionType connectionType) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setSpacing(5);
        AttachmentLink attachmentLink = new AttachmentLink(connectionType, ConnectionGender.MALE);
        Label label = new Label();
        label.setText("Attach");
        label.setTextFill(Paint.valueOf("lightgrey"));
        hbox.getChildren().addAll(label, attachmentLink);
        right.getChildren().add(hbox);
        rightAttachments.add(attachmentLink);
    }

    public List<Node> getAttachmentNodes() {
        List<Node> nodes = new ArrayList<>();
        for (AttachmentLink link : leftAttachments) {
            List<Node> linkedNodes = link.getConnectedNodes();
            if (!linkedNodes.isEmpty()) {
                nodes.add(linkedNodes.getFirst());
            }
        }
        return nodes;
    }

    public List<Node> getAttacherNodes() {
        List<Node> nodes = new ArrayList<>();
        for (AttachmentLink link : rightAttachments) {
            nodes.addAll(link.getConnectedNodes());
        }
        return nodes;
    }

    @Override
    public void onParentDragged() {
        for (AttachmentLink link : leftAttachments) {
            link.onParentDragged();
        }
        for (AttachmentLink link : rightAttachments) {
            link.onParentDragged();
        }
    }
}
