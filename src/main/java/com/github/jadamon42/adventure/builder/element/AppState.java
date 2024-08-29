package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.state.MainBoardState;
import com.github.jadamon42.adventure.builder.state.MainBoardStateBuilder;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class AppState {
    private static AppState instance;
    private double scaleFactor = 1.0;
    private Pane mainBoard = null;

    private AppState() {}

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public Bounds getMainBoardBounds() {
        Bounds bounds = null;
        if (mainBoard != null) {
            bounds = mainBoard.getLayoutBounds();
        }
        return bounds;
    }

    public void setMainBoard(Pane mainBoard) {
        this.mainBoard = mainBoard;
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
}
