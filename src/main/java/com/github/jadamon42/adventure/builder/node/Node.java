package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.AppState;
import com.github.jadamon42.adventure.builder.element.DraggableChild;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.UUID;

public abstract class Node extends VBox {
    private final DragContext dragContext;

    public Node() {
        setId(UUID.randomUUID().toString());
        dragContext = new DragContext();
        makeDraggable();
    }

    public <T> T getFirst(List<T> list) {
        return list.isEmpty() ? null : list.getFirst();
    }

    public <T> T getLast(List<T> list) {
        return list.isEmpty() ? null : list.getLast();
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
            notifyChildrenOfDrag();
        });

        setOnMouseReleased(mouseEvent -> getScene().setCursor(Cursor.DEFAULT));
    }

    private void notifyChildrenOfDrag() {
        getChildren().forEach(child -> {
            if (child instanceof DraggableChild draggableChild) {
                draggableChild.onParentDragged();
            }
        });
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

    public abstract String getTitle();

    public abstract void setTitle(String title);
}
