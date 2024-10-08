package com.github.jadamon42.adventure.builder.element.connection;

import com.github.jadamon42.adventure.builder.AppState;
import com.github.jadamon42.adventure.builder.element.InformableChild;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.UUID;

public class ConnectionLine extends Line implements InformableChild {
    private ConnectionPoint malePoint;
    private ConnectionPoint femalePoint;
    private Color focusColor;
    private double focusWidth;
    private Color originalColor;
    private double startingWidth;

    public ConnectionLine() {}

    public ConnectionLine(ConnectionPoint startingPoint) {
        setId(UUID.randomUUID().toString());
        if (startingPoint.getGender() == ConnectionGender.MALE) {
            malePoint = startingPoint;
        } else {
            femalePoint = startingPoint;
        }
        init();
    }

    public void init() {
        ConnectionType type = getType();
        setId(UUID.randomUUID().toString());
        originalColor = (Color) type.getConfig().color();
        startingWidth = type.getConfig().width();
        focusColor = Color.web("#5a3d6b");
        focusWidth = startingWidth + 2.0;
        setStrokeWidth(startingWidth);
        setStroke(originalColor);
        setCursor(Cursor.HAND);
        setOnMouseClicked(this::handleClick);
        focusedProperty().addListener(this::focusListener);
        sceneProperty().addListener(this::sceneListener);
        update();
    }

    private void handleClick(MouseEvent event) {
        requestFocus();
    }

    private void focusListener(ObservableValue<? extends Boolean> obs, Boolean wasFocused, Boolean isNowFocused) {
        if (isNowFocused) {
            setStroke(focusColor);
            setStrokeWidth(focusWidth);
        } else {
            setStroke(originalColor);
            setStrokeWidth(startingWidth);
        }
    }

    private void sceneListener(ObservableValue<? extends javafx.scene.Scene> obs, javafx.scene.Scene oldScene, javafx.scene.Scene newScene) {
        if (newScene != null) {
            newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        }
    }

    private void handleKeyPress(KeyEvent event) {
        if (isFocused() && (event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE)) {
            delete();
        } else if (isFocused() && event.getCode() == KeyCode.ESCAPE) {
            setFocused(false);
        }
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

    public ConnectionType getType() {
        return malePoint != null ? malePoint.getType() : femalePoint.getType();
    }

    public void update() {
        if (malePoint != null) {
            Point2D malePointInMainBoard = AppState.getInstance().getMainBoardPointFromNode(malePoint);
            setStartX(malePointInMainBoard.getX());
            setStartY(malePointInMainBoard.getY());
        }
        if (femalePoint != null) {
            Point2D femalePointInMainBoard = AppState.getInstance().getMainBoardPointFromNode(femalePoint);
            setEndX(femalePointInMainBoard.getX());
            setEndY(femalePointInMainBoard.getY());
        }
    }

    public void startFollowingCursor() {
        ConnectionPoint singlePoint = malePoint != null ? malePoint : femalePoint;
        Point2D startPointOnMainBoard = AppState.getInstance().getMainBoardPointFromNode(singlePoint);
        updateOtherEnd(startPointOnMainBoard.getX(), startPointOnMainBoard.getY());

        final double offset = 5.0;

        getScene().setOnMouseMoved(event -> {
            Point2D endPointOnMainBoard = AppState.getInstance().getMainBoardPointFromScene(event.getSceneX(), event.getSceneY());

            double deltaX = endPointOnMainBoard.getX() - startPointOnMainBoard.getX();
            double deltaY = endPointOnMainBoard.getY() - startPointOnMainBoard.getY();
            double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (length != 0) {
                double offsetX = (deltaX / length) * offset;
                double offsetY = (deltaY / length) * offset;
                endPointOnMainBoard = endPointOnMainBoard.subtract(offsetX, offsetY);
            }

            singlePoint.setLayoutX(endPointOnMainBoard.getX());
            singlePoint.setLayoutY(endPointOnMainBoard.getY());
            updateOtherEnd(endPointOnMainBoard.getX(), endPointOnMainBoard.getY());
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

    @Override
    public void onParentDeleted() {
        delete();
    }

    private void delete() {
        malePoint.removeConnection(this);
        femalePoint.removeConnection(this);
        AppState.getInstance().removeChildFromMainBoard(this);
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
