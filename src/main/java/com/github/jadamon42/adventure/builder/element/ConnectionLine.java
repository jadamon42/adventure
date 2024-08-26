package com.github.jadamon42.adventure.builder.element;

import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ConnectionLine extends Line {
    private ConnectionPoint malePoint;
    private ConnectionPoint femalePoint;

    public ConnectionLine(ConnectionPoint malePoint, ConnectionPoint femalePoint) {
        this.malePoint = malePoint;
        this.femalePoint = femalePoint;
        setStrokeWidth(3.0);
        setStroke(Color.BLUE);
        setMouseTransparent(true);
        update();
    }

    public void setMalePoint(ConnectionPoint malePoint) {
        this.malePoint = malePoint;
    }

    public void setFemalePoint(ConnectionPoint femalePoint) {
        this.femalePoint = femalePoint;
    }

    public void update() {
        Pane mainBoard = ConnectionManager.getInstance().getCommonParent();
        if (malePoint != null) {
            Bounds maleBoundsInScene = malePoint.localToScene(malePoint.getBoundsInLocal());
            Bounds maleBoundsInMainBoard = mainBoard.sceneToLocal(maleBoundsInScene);
            setStartX(maleBoundsInMainBoard.getMinX() + maleBoundsInMainBoard.getWidth() / 2);
            setStartY(maleBoundsInMainBoard.getMinY() + maleBoundsInMainBoard.getHeight() / 2);
        }
        if (femalePoint != null) {
            Bounds femaleBoundsInScene = femalePoint.localToScene(femalePoint.getBoundsInLocal());
            Bounds femaleBoundsInMainBoard = mainBoard.sceneToLocal(femaleBoundsInScene);
            setEndX(femaleBoundsInMainBoard.getMinX() + femaleBoundsInMainBoard.getWidth() / 2);
            setEndY(femaleBoundsInMainBoard.getMinY() + femaleBoundsInMainBoard.getHeight() / 2);
        }
    }

    public void updateEnd(double x, double y) {
        setEndX(x);
        setEndY(y);
    }

    public void startFollowingCursor() {
        ConnectionPoint singlePoint = malePoint != null ? malePoint : femalePoint;
        Pane mainBoard = ConnectionManager.getInstance().getCommonParent();
        Bounds pointBoundsInScene = singlePoint.localToScene(singlePoint.getBoundsInLocal());
        Bounds pointBoundsInMainBoard = mainBoard.sceneToLocal(pointBoundsInScene);
        double startX = pointBoundsInMainBoard.getMinX() + pointBoundsInMainBoard.getWidth() / 2;
        double startY = pointBoundsInMainBoard.getMinY() + pointBoundsInMainBoard.getHeight() / 2;
        updateEnd(startX, startY);

        getScene().setOnMouseMoved(event -> {
            double xInMainBoard = mainBoard.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
            double yInMainBoard = mainBoard.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();
            singlePoint.setLayoutX(xInMainBoard);
            singlePoint.setLayoutY(yInMainBoard);
            updateEnd(xInMainBoard, yInMainBoard);
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
