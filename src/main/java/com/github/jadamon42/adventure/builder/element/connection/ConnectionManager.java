package com.github.jadamon42.adventure.builder.element.connection;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ConnectionManager {
    private static ConnectionManager instance;
    private ConnectionPoint selectedConnectionPoint;
    private ConnectionLine currentConnectionLine;
    private Pane commonParent;

    private ConnectionManager() {}

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public void setCommonParent(Pane commonParent) {
        this.commonParent = commonParent;
    }

    public Point2D getMainBoardPointFromNode(Node node) {
        Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
        double centerX = boundsInScene.getMinX() + boundsInScene.getWidth() / 2;
        double centerY = boundsInScene.getMinY() + boundsInScene.getHeight() / 2;
        return getMainBoardPointFromScene(centerX, centerY);

    }

    public Point2D getMainBoardPointFromScene(double sceneX, double sceneY) {
        return commonParent.sceneToLocal(sceneX, sceneY);
    }

    public void handleAttachmentClick(ConnectionPoint point) {
        if (selectedConnectionPoint == null) {
            selectedConnectionPoint = point;
            currentConnectionLine = new ConnectionLine(point);
            commonParent.getChildren().add(currentConnectionLine);
            currentConnectionLine.startFollowingCursor();
        } else {
            if (selectedConnectionPoint.canConnectTo(point)) {
                currentConnectionLine.stopFollowingCursor();
                attachLinks(selectedConnectionPoint, point);
                currentConnectionLine = null;
                selectedConnectionPoint = null;
            }
        }
    }

    public boolean canSelect(ConnectionPoint point) {
        if (selectedConnectionPoint == null) {
            return point.canConnect();
        } else {
            return selectedConnectionPoint.canConnectTo(point);
        }
    }

    private void attachLinks(ConnectionPoint link1, ConnectionPoint link2) {
        if (link1.getGender() == ConnectionGender.MALE) {
            currentConnectionLine.setFemalePoint(link2);
        } else {
            currentConnectionLine.setMalePoint(link2);
        }
        currentConnectionLine.update();
        link1.addConnection(currentConnectionLine);
        link2.addConnection(currentConnectionLine);
    }

    public void cancelCurrentLine() {
        if (currentConnectionLine != null) {
            currentConnectionLine.stopFollowingCursor();
            commonParent.getChildren().remove(currentConnectionLine);
            selectedConnectionPoint = null;
            currentConnectionLine = null;
        }
    }

    public void removeConnectionLine(ConnectionLine line) {
        commonParent.getChildren().remove(line);
    }
}
