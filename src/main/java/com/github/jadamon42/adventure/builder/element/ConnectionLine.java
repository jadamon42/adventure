package com.github.jadamon42.adventure.builder.element;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;

public class ConnectionLine extends Line {
    private ConnectionPoint malePoint;
    private ConnectionPoint femalePoint;

    public ConnectionLine(ConnectionPoint malePoint, ConnectionPoint femalePoint) {
        this.malePoint = malePoint;
        this.femalePoint = femalePoint;
        setStrokeWidth(this.malePoint.getType().getConfig().width());
        setStroke(this.malePoint.getType().getConfig().color());
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
        if (malePoint != null) {
            Bounds maleBoundsInMainBoard = ConnectionManager.getInstance().getMainBoardBoundsOfNode(malePoint);
            setStartX(maleBoundsInMainBoard.getMinX() + maleBoundsInMainBoard.getWidth() / 2);
            setStartY(maleBoundsInMainBoard.getMinY() + maleBoundsInMainBoard.getHeight() / 2);
        }
        if (femalePoint != null) {
            Bounds femaleBoundsInMainBoard = ConnectionManager.getInstance().getMainBoardBoundsOfNode(femalePoint);
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
        Bounds pointBoundsInMainBoard = ConnectionManager.getInstance().getMainBoardBoundsOfNode(singlePoint);
        double startX = pointBoundsInMainBoard.getMinX() + pointBoundsInMainBoard.getWidth() / 2;
        double startY = pointBoundsInMainBoard.getMinY() + pointBoundsInMainBoard.getHeight() / 2;
        updateEnd(startX, startY);

        getScene().setOnMouseMoved(event -> {
            Point2D pointOnMainBoard = ConnectionManager.getInstance().getMainBoardPointFromScene(event.getSceneX(), event.getSceneY());
            singlePoint.setLayoutX(pointOnMainBoard.getX());
            singlePoint.setLayoutY(pointOnMainBoard.getY());
            updateEnd(pointOnMainBoard.getX(), pointOnMainBoard.getY());
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
