package com.github.jadamon42.adventure.builder.element;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    private static ConnectionManager instance;
    private ConnectionPoint selectedConnectionPoint;
    private Connection currentConnection;
    private final List<Connection> connections = new ArrayList<>();
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

    public Pane getCommonParent() {
        return commonParent;
    }

    public void handleAttachmentClick(ConnectionPoint point) {
        if (selectedConnectionPoint == null) {
            selectedConnectionPoint = point;
            currentConnection = new Connection(point, null);
            connections.add(currentConnection);
            commonParent.getChildren().add(currentConnection);
            selectedConnectionPoint.startFollowingCursor(currentConnection);
        } else {
            if (canAttach(selectedConnectionPoint, point)) {
                selectedConnectionPoint.stopFollowingCursor();
                attachLinks(selectedConnectionPoint, point);
            }
        }
    }

    private boolean canAttach(ConnectionPoint point1, ConnectionPoint point2) {
        return point1.getType() == point2.getType() &&
               point1.getGender() != point2.getGender();
    }

    private void attachLinks(ConnectionPoint link1, ConnectionPoint link2) {
        if (link1.getGender() == ConnectionGender.MALE) {
            currentConnection.setFemaleLink(link2);
        } else {
            currentConnection.setMaleLink(link2);
        }
        currentConnection.update();
        link1.setIsConnected(true);
        link2.setIsConnected(true);
    }

    public void cancelCurrentLine() {
        if (selectedConnectionPoint != null) {
            selectedConnectionPoint.stopFollowingCursor();
            commonParent.getChildren().remove(currentConnection);
            connections.remove(currentConnection);
            selectedConnectionPoint = null;
            currentConnection = null;
        }
    }

    public void updateLines() {
        for (Connection line : connections) {
            line.update();
        }
    }
}
