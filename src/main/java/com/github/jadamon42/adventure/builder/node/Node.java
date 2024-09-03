package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.AppState;
import com.github.jadamon42.adventure.builder.element.DraggableChild;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.util.List;
import java.util.UUID;

public abstract class Node extends VBox {
    private final DragContext dragContext;

    public Node() {
        setId(UUID.randomUUID().toString());
        dragContext = new DragContext();
        makeDraggable();
        makeDeletable();
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
                requestFocus();
                dragContext.startDrag(mouseEvent.getSceneX(), mouseEvent.getSceneY(), getLayoutX(), getLayoutY());
                mouseEvent.consume();
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

    private void makeDeletable() {
        focusedProperty().addListener(this::focusListener);
        sceneProperty().addListener(this::sceneListener);
    }

    private void focusListener(ObservableValue<? extends Boolean> obs, Boolean wasFocused, Boolean isNowFocused) {
        if (isNowFocused) {
            setStyle("-fx-border-color: #2980b9;");
        } else {
            setStyle("");
        }
    }

    void sceneListener(ObservableValue<? extends javafx.scene.Scene> obs, javafx.scene.Scene oldScene, javafx.scene.Scene newScene) {
        if (newScene != null) {
            newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        }
    }

    private void handleKeyPress(KeyEvent event) {
        if (isFocused() && (event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE)) {
            if (getParent() != null && getParent() instanceof Pane) {
                ((Pane) getParent()).getChildren().remove(this);
            }
        } else if (isFocused() && event.getCode() == KeyCode.ESCAPE) {
            setFocused(false);
        }
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
