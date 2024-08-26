package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.node.Node;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public abstract class ConnectionPoint extends HBox {
    private final ConnectionGender connectionGender;
    private boolean isConnected;

    public ConnectionPoint(ConnectionGender connectionGender) {
        this.connectionGender = connectionGender;
        setMinWidth(HBox.USE_PREF_SIZE);
        setMinHeight(HBox.USE_PREF_SIZE);
        setMaxWidth(HBox.USE_PREF_SIZE);
        setMaxHeight(HBox.USE_PREF_SIZE);
        setOnMouseEntered(this::handeMouseEnter);
        setOnMouseClicked(this::handleClick);
    }

    public ConnectionGender getGender() {
        return connectionGender;
    }

    public abstract ConnectionType getType();

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
        if (!canConnect()) {
            setCursor(Cursor.DEFAULT);
        }
    }

    public boolean canConnect() {
        ConnectionType.ConnectionRelationship relationship = getType().getConfig().desiredRelationship();
        boolean canConnect;
        switch (relationship) {
            case ONE_TO_ONE -> canConnect = !isConnected;
            case ONE_TO_MANY -> canConnect = (connectionGender == ConnectionGender.FEMALE && !isConnected)
                                          || (connectionGender == ConnectionGender.MALE);
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
            case ONE_TO_MANY -> canConnect &= (connectionGender == ConnectionGender.FEMALE && !isConnected)
                                           || (connectionGender == ConnectionGender.MALE);
            default -> canConnect = false;
        }
        return canConnect;
    }

    private void handleClick(MouseEvent event) {
        event.consume();
        if (connectionGender == ConnectionGender.FEMALE && isConnected) {
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
}
