package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.node.Node;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public abstract class ConnectionPoint extends HBox implements DraggableChild {
    private final List<ConnectionLine> connections;
    private final ConnectionGender gender;
    private boolean isConnected;

    public ConnectionPoint(ConnectionGender gender) {
        this.connections = new ArrayList<>();
        this.gender = gender;
        this.isConnected = false;
        setMinWidth(HBox.USE_PREF_SIZE);
        setMinHeight(HBox.USE_PREF_SIZE);
        setMaxWidth(HBox.USE_PREF_SIZE);
        setMaxHeight(HBox.USE_PREF_SIZE);
        setOnMouseEntered(this::handeMouseEnter);
        setOnMouseClicked(this::handleClick);
    }

    public ConnectionGender getGender() {
        return gender;
    }

    public abstract ConnectionType getType();

    public List<ConnectionLine> getConnections() {
        return connections;
    }

    public void addConnection(ConnectionLine line) {
        if (!connections.contains(line)) {
            connections.add(line);
            setIsConnected(true);
        }
    }

    public void removeConnection(ConnectionLine line) {
        connections.remove(line);
        if (connections.isEmpty()) {
            setIsConnected(false);
        }
    }

    public boolean canConnect() {
        ConnectionType.ConnectionRelationship relationship = getType().getConfig().desiredRelationship();
        boolean canConnect;
        switch (relationship) {
            case ONE_TO_ONE -> canConnect = !isConnected;
            case ONE_TO_MANY -> canConnect = (gender == ConnectionGender.FEMALE && !isConnected)
                                          || (gender == ConnectionGender.MALE);
            case MANY_TO_ONE -> canConnect = (gender == ConnectionGender.MALE && !isConnected)
                                          || (gender == ConnectionGender.FEMALE);
            default -> canConnect = false;
        }
        return canConnect;
    }

    public boolean canConnectTo(ConnectionPoint other) {
        ConnectionType.ConnectionRelationship relationship = getType().getConfig().desiredRelationship();
        boolean canConnect = getType() == other.getType()
                          && getGender() != other.getGender();
        switch (relationship) {
            case ONE_TO_ONE -> canConnect &= !isConnected;
            case ONE_TO_MANY -> canConnect &= (gender == ConnectionGender.FEMALE && !isConnected)
                                           || (gender == ConnectionGender.MALE);
            case MANY_TO_ONE -> canConnect &= (gender == ConnectionGender.MALE && !isConnected)
                                           || (gender == ConnectionGender.FEMALE);
            default -> canConnect = false;
        }
        return canConnect;
    }

    @Override
    public void onParentDragged() {
        for (ConnectionLine line : connections) {
            line.onParentDragged();
        }
    }

    private void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
        if (!canConnect()) {
            setCursor(Cursor.DEFAULT);
        }
    }

    private void handleClick(MouseEvent event) {
        event.consume();
        if (!canConnect()) {
            return;
        }
        ConnectionManager.getInstance().handleAttachmentClick(this);
    }

    private void handeMouseEnter(MouseEvent event) {
        if (ConnectionManager.getInstance().canSelect(this)) {
            setCursor(Cursor.HAND);
        }
    }

    public Node getParentNode() {
        Parent parent = getParent();
        while (parent != null && !(parent instanceof Node)) {
            parent = parent.getParent();
        }
        return (Node) parent;
    }

    public List<Node> getConnectedNodes() {
        List<Node> connectedNodes = new ArrayList<>();
        for (ConnectionLine connection : connections) {
            ConnectionPoint other;
            if (gender == ConnectionGender.MALE) {
                other = connection.getFemalePoint();
            } else {
                other = connection.getMalePoint();
            }
            connectedNodes.add(other.getParentNode());
        }
        return connectedNodes;
    }
}
