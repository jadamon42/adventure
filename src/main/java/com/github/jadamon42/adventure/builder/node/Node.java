package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.AppState;
import com.github.jadamon42.adventure.builder.element.ConnectionManager;
import com.github.jadamon42.adventure.model.Effect;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.util.UUID;

public class Node extends VBox {
    private final DragContext dragContext;

    public Node() {
        setId(UUID.randomUUID().toString());
        dragContext = new DragContext();
        makeDraggable();
    }

    private void makeDraggable() {
        setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                dragContext.startDrag(mouseEvent.getSceneX(), mouseEvent.getSceneY(), getLayoutX(), getLayoutY());
                getScene().setCursor(Cursor.CLOSED_HAND);
            }
        });

        setOnMouseDragged(mouseEvent -> {
            mouseEvent.consume();
            double newX = dragContext.calculateNewX(mouseEvent.getSceneX());
            double newY = dragContext.calculateNewY(mouseEvent.getSceneY());

            Bounds bounds = AppState.getInstance().getMainBoardBounds();
            double maxX = (bounds.getWidth() - getWidth());
            double maxY = (bounds.getHeight() - getHeight());

            newX = Math.max(0, Math.min(newX, maxX));
            newY = Math.max(0, Math.min(newY, maxY));

            setLayoutX(newX);
            setLayoutY(newY);
            ConnectionManager.getInstance().updateLines();
        });

        setOnMouseReleased(mouseEvent -> getScene().setCursor(Cursor.DEFAULT));
    }

    private static class DragContext {
        private double offsetX, offsetY;
        private double scaleFactor;

        public void startDrag(double sceneX, double sceneY, double layoutX, double layoutY) {
            scaleFactor = AppState.getInstance().getScaleFactor();
            offsetX = (sceneX - (layoutX * scaleFactor)) / scaleFactor;
            offsetY = (sceneY - (layoutY * scaleFactor)) / scaleFactor;
        }

        public double calculateNewX(double sceneX) {
            return (sceneX / scaleFactor) - offsetX;
        }

        public double calculateNewY(double sceneY) {
            return (sceneY / scaleFactor) - offsetY;
        }
    }
}
