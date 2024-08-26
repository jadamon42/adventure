package com.github.jadamon42.adventure.builder.element;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class ConnectionPoint extends HBox {
    private final ConnectionGender connectionGender;
    private boolean isConnected;

    public ConnectionPoint(ConnectionGender connectionGender) {
        this.connectionGender = connectionGender;
        setMinWidth(HBox.USE_PREF_SIZE);
        setMinHeight(HBox.USE_PREF_SIZE);
        setMaxWidth(HBox.USE_PREF_SIZE);
        setMaxHeight(HBox.USE_PREF_SIZE);
        setCursor(Cursor.HAND);
        setOnMouseClicked(this::handleClick);
    }

    public ConnectionGender getGender() {
        return connectionGender;
    }

    public abstract ConnectionType getType();

    public void setIsConnected(boolean isConnected) {
        if (connectionGender == ConnectionGender.FEMALE) {
            setCursor(Cursor.DEFAULT);
        }
        this.isConnected = isConnected;
    }

    private void handleClick(MouseEvent event) {
        event.consume();
        if (connectionGender == ConnectionGender.FEMALE && isConnected) {
            return;
        }
        ConnectionManager.getInstance().handleAttachmentClick(this);
    }

    public void startFollowingCursor(Connection line) {
        Pane mainBoard = ConnectionManager.getInstance().getCommonParent();
        Bounds maleBoundsInScene = localToScene(getBoundsInLocal());
        Bounds maleBoundsInMainBoard = mainBoard.sceneToLocal(maleBoundsInScene);
        double startX = maleBoundsInMainBoard.getMinX() + maleBoundsInMainBoard.getWidth() / 2;
        double startY = maleBoundsInMainBoard.getMinY() + maleBoundsInMainBoard.getHeight() / 2;
        line.updateEnd(startX, startY);

        getScene().setOnMouseMoved(event -> {
            double xInMainBoard = mainBoard.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
            double yInMainBoard = mainBoard.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();
            setLayoutX(xInMainBoard);
            setLayoutY(yInMainBoard);
            line.updateEnd(xInMainBoard, yInMainBoard);
        });

        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                ConnectionManager.getInstance().cancelCurrentLine();
            }
        });
    }

    public void stopFollowingCursor() {
        if (getScene() != null) {
            getScene().setOnMouseMoved(null);
            getScene().setOnKeyPressed(null);
        }
    }
}
