package com.github.jadamon42.adventure.builder.element;

import javafx.scene.Cursor;
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
        if (connectionGender == ConnectionGender.FEMALE) {
            setCursor(Cursor.DEFAULT);
        }
        this.isConnected = isConnected;
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
}
