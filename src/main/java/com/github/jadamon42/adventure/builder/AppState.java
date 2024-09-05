package com.github.jadamon42.adventure.builder;

import com.github.jadamon42.adventure.builder.element.VisitableNode;
import com.github.jadamon42.adventure.builder.element.ZoomableScrollPane;
import com.github.jadamon42.adventure.builder.state.MainBoardState;
import com.github.jadamon42.adventure.builder.state.MainBoardStateBuilder;
import javafx.application.Platform;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class AppState {
    private static AppState instance;
    private Pane mainBoard = null;
    private ZoomableScrollPane zoomableScrollPane = null;

    private AppState() {}

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void setMainBoard(Pane mainBoard) {
        this.mainBoard = mainBoard;
    }

    public void setZoomableScrollPane(ZoomableScrollPane zoomableScrollPane) {
        this.zoomableScrollPane = zoomableScrollPane;
    }

    public double getScaleFactor() {
        return zoomableScrollPane.scaleValueProperty().get();
    }

    public Bounds getMainBoardBounds() {
        Bounds bounds = null;
        if (mainBoard != null) {
            bounds = mainBoard.getLayoutBounds();
        }
        return bounds;
    }

    public Point2D getMainBoardPointFromNode(Node node) {
        Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
        double centerX = boundsInScene.getMinX() + boundsInScene.getWidth() / 2;
        double centerY = boundsInScene.getMinY() + boundsInScene.getHeight() / 2;
        return getMainBoardPointFromScene(centerX, centerY);

    }

    public Point2D getMainBoardPointFromScene(double sceneX, double sceneY) {
        return mainBoard.sceneToLocal(sceneX, sceneY);
    }

    public MainBoardState getMainBoardState() {
        MainBoardStateBuilder builder = new MainBoardStateBuilder();
        for (Node node : mainBoard.getChildren()) {
            if (node instanceof VisitableNode visitableNode) {
                visitableNode.accept(builder);
            }
        }
        return builder.build();
    }

    public void addChildToMainBoard(Node newNode) {
        mainBoard.getChildren().add(newNode);
        Platform.runLater(mainBoard::layout);
    }

    public void centerNode(Node newNode) {
        Platform.runLater(() -> {
            Bounds visibleBounds = getVisibleBounds();
            double centerX = visibleBounds.getMinX()
                             + (visibleBounds.getWidth() / 2)
                             - (newNode.getLayoutBounds().getWidth() / 2);
            double centerY = visibleBounds.getMinY()
                             + (visibleBounds.getHeight() / 2)
                             - newNode.getLayoutBounds().getHeight() / 2;
            newNode.setLayoutX(centerX);
            newNode.setLayoutY(centerY);
        });
    }

    public void removeChildFromMainBoard(Node child) {
        mainBoard.getChildren().remove(child);
    }

    private Bounds getVisibleBounds() {
        Bounds viewportBounds = zoomableScrollPane.getViewportBounds();

        double scaleFactor = getScaleFactor();
        double visibleX = zoomableScrollPane.getHvalue() * (mainBoard.getWidth() - viewportBounds.getWidth() / scaleFactor);
        double visibleY = zoomableScrollPane.getVvalue() * (mainBoard.getHeight() - viewportBounds.getHeight() / scaleFactor);
        double visibleWidth = viewportBounds.getWidth() / scaleFactor;
        double visibleHeight = viewportBounds.getHeight() / scaleFactor;

        return new BoundingBox(visibleX, visibleY, visibleWidth, visibleHeight);
    }
}
