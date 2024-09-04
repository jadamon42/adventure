package com.github.jadamon42.adventure.builder.element.connection;

import com.github.jadamon42.adventure.builder.AppState;

public class ConnectionManager {
    private static ConnectionManager instance;
    private ConnectionPoint selectedConnectionPoint;
    private ConnectionLine currentConnectionLine;

    private ConnectionManager() {}

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public void handleAttachmentClick(ConnectionPoint point) {
        if (selectedConnectionPoint == null) {
            selectedConnectionPoint = point;
            currentConnectionLine = new ConnectionLine(point);
            AppState.getInstance().addChildToMainBoard(currentConnectionLine);
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
        link1.addConnection(currentConnectionLine);
        link2.addConnection(currentConnectionLine);
    }

    public void cancelCurrentLine() {
        if (currentConnectionLine != null) {
            currentConnectionLine.stopFollowingCursor();
            AppState.getInstance().removeChildFromMainBoard(currentConnectionLine);
            selectedConnectionPoint = null;
            currentConnectionLine = null;
        }
    }
}
