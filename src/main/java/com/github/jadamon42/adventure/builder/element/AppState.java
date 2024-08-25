// Create AppState.java
package com.github.jadamon42.adventure.builder.element;

import javafx.geometry.Bounds;

public class AppState {
    private static AppState instance;
    private double scaleFactor = 1.0;
    private Bounds mainBoardBounds = null;

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
        return mainBoardBounds;
    }

    public void setMainBoardBounds(Bounds bounds) {
        mainBoardBounds = bounds;
    }
}
