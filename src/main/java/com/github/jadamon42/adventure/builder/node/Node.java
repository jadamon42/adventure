package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.AppState;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

class Node extends VBox {
    public Node() {
        makeDraggable();
    }

    private void makeDraggable() {
        final Delta dragDelta = new Delta();

        setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                double scaleFactor = AppState.getInstance().getScaleFactor();
                dragDelta.x = (mouseEvent.getSceneX() - (getLayoutX() * scaleFactor)) / scaleFactor;
                dragDelta.y = (mouseEvent.getSceneY() - (getLayoutY() * scaleFactor)) / scaleFactor;
                getScene().setCursor(Cursor.CLOSED_HAND);
            }

        });

        setOnMouseDragged(mouseEvent -> {
            mouseEvent.consume();
            double scaleFactor = AppState.getInstance().getScaleFactor();
            double newX = (mouseEvent.getSceneX() / scaleFactor) - dragDelta.x;
            double newY = (mouseEvent.getSceneY() / scaleFactor) - dragDelta.y;

            // Prevent the box from being dragged outside the main viewport bounds
            Bounds bounds = AppState.getInstance().getMainBoardBounds();
            double maxX = (bounds.getWidth() - getWidth());
            double maxY = (bounds.getHeight() - getHeight());

            newX = Math.max(0, Math.min(newX, maxX));
            newY = Math.max(0, Math.min(newY, maxY));

            setLayoutX(newX);
            setLayoutY(newY);
        });

        setOnMouseReleased(mouseEvent -> getScene().setCursor(Cursor.DEFAULT));
    }

    private static class Delta {
        double x, y;
    }
}
