package com.github.jadamon42.adventure.builder.node;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class NodeFactory {
    public static Node createNode(String nodeClassName) {
        NodeType nodeType = NodeType.fromString(nodeClassName);
        if (nodeType == null) {
            throw new IllegalArgumentException("Unknown node type: " + nodeClassName);
        }
        return nodeType.getSupplier().get();
    }

    public static WritableImage getNodeSnapshot(String nodeClassName) {
        Node node = createNode(nodeClassName);

        Scene tempScene = new Scene(new Group(node));
        String stylesheet = NodeFactory.class.getResource("/buildui.css").toExternalForm();
        tempScene.getStylesheets().add(stylesheet);

        tempScene.getRoot().applyCss();
        tempScene.getRoot().layout();

        int contentWidth = (int) Math.ceil(node.getWidth());
        int contentHeight = (int) Math.ceil(node.getHeight());

        WritableImage snapshot = new WritableImage(contentWidth, contentHeight);

        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        snapshotParameters.setViewport(new javafx.geometry.Rectangle2D(
                0,
                0,
                contentWidth,
                contentHeight
        ));

        node.snapshot(snapshotParameters, snapshot);

        return snapshot;
    }
}
