package com.github.jadamon42.adventure.builder.element.connection;

import com.github.jadamon42.adventure.builder.element.DraggableChild;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;

public class ConnectionLine extends Line implements DraggableChild {
    private ConnectionPoint malePoint;
    private ConnectionPoint femalePoint;

    public ConnectionLine(ConnectionPoint startingPoint) {
        if (startingPoint.getGender() == ConnectionGender.MALE) {
            malePoint = startingPoint;
        } else {
            femalePoint = startingPoint;
        }
        setStrokeWidth(startingPoint.getType().getConfig().width());
        setStroke(startingPoint.getType().getConfig().color());
        setMouseTransparent(true);
        update();
    }

    public void setMalePoint(ConnectionPoint malePoint) {
        this.malePoint = malePoint;
    }

    public ConnectionPoint getMalePoint() {
        return malePoint;
    }

    public void setFemalePoint(ConnectionPoint femalePoint) {
        this.femalePoint = femalePoint;
    }

    public ConnectionPoint getFemalePoint() {
        return femalePoint;
    }

    public void update() {
        if (malePoint != null) {
            Point2D malePointInMainBoard = ConnectionManager.getInstance().getMainBoardPointFromNode(malePoint);
            setStartX(malePointInMainBoard.getX());
            setStartY(malePointInMainBoard.getY());
        }
        if (femalePoint != null) {
            Point2D femalePointInMainBoard = ConnectionManager.getInstance().getMainBoardPointFromNode(femalePoint);
            setEndX(femalePointInMainBoard.getX());
            setEndY(femalePointInMainBoard.getY());
        }
    }

    public void startFollowingCursor() {
        ConnectionPoint singlePoint = malePoint != null ? malePoint : femalePoint;
        Point2D pointInMainBoard = ConnectionManager.getInstance().getMainBoardPointFromNode(singlePoint);
        updateOtherEnd(pointInMainBoard.getX(), pointInMainBoard.getY());

        getScene().setOnMouseMoved(event -> {
            Point2D pointOnMainBoard = ConnectionManager.getInstance().getMainBoardPointFromScene(event.getSceneX(), event.getSceneY());
            singlePoint.setLayoutX(pointOnMainBoard.getX());
            singlePoint.setLayoutY(pointOnMainBoard.getY());
            updateOtherEnd(pointOnMainBoard.getX(), pointOnMainBoard.getY());
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

    @Override
    public void onParentDragged() {
        update();
    }

    private void updateOtherEnd(double x, double y) {
        if (malePoint == null) {
            setStartX(x);
            setStartY(y);
        } else {
            setEndX(x);
            setEndY(y);
        }
    }
}
