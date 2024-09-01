package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.node.Node;
import com.github.jadamon42.adventure.builder.node.NodeFactory;
import com.github.jadamon42.adventure.util.ImageUtils;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;

public class NodeIconButton extends VBox {
    private final Class<? extends  Node> nodeClass;

    public NodeIconButton(Class<? extends Node> nodeClass) {
        this.nodeClass = nodeClass;

        setSpacing(5);
        setAlignment(Pos.CENTER);
        setCursor(Cursor.HAND);

        WritableImage icon = NodeFactory.getNodeSnapshot(nodeClass.getSimpleName());
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(70);
        imageView.setPreserveRatio(true);

        Label label = new Label(nodeClass.getSimpleName());
        label.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: lightgrey;");

        getChildren().addAll(imageView, label);

        setOnDragDetected(this::onDragDetected);
        setOnDragDone(this::onDragDone);
        setOnMouseClicked(this::onMouseClicked);
    }

    private void onDragDetected(MouseEvent event) {
        Dragboard db = startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(nodeClass.getSimpleName());
        db.setContent(content);

        WritableImage snapshot = NodeFactory.getNodeSnapshot(nodeClass.getSimpleName());
        ImageUtils.setOpacity(snapshot, 0.75);

        Image dragView = new WritableImage(snapshot.getPixelReader(), 0, 0, (int) snapshot.getWidth(), (int) snapshot.getHeight());
        Image scaledDragView = ImageUtils.scaleImage(dragView, 0.5);
        db.setDragView(scaledDragView, scaledDragView.getWidth() / 2, scaledDragView.getHeight() / 2);

        setOpacity(0.5);
        event.consume();
    }

    private void onDragDone(DragEvent event) {
        setOpacity(1.0);
        event.consume();
    }

    private void onMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Node newNode = NodeFactory.createNode(nodeClass.getSimpleName());

            AppState.getInstance().addChildToMainBoard(newNode);
            AppState.getInstance().centerNode(newNode);
        }
    }
}
