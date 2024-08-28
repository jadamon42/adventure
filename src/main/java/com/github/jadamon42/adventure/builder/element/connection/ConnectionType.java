package com.github.jadamon42.adventure.builder.element.connection;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum ConnectionType {
    NODE(new ConnectionConfig(Color.web("white"), 6.0, ConnectionRelationship.MANY_TO_ONE)),
    ITEM(new ConnectionConfig(Color.web("#2980b9"), 3.0, ConnectionRelationship.ONE_TO_MANY)),
    EFFECT(new ConnectionConfig(Color.web("#2980b9"), 3.0, ConnectionRelationship.ONE_TO_MANY)),
    CONDITION(new ConnectionConfig(Color.web("#2980b9"), 3.0, ConnectionRelationship.ONE_TO_MANY)),
    HANDLER(new ConnectionConfig(Color.web("#2980b9"), 3.0, ConnectionRelationship.ONE_TO_MANY));

    private final ConnectionConfig config;

    ConnectionType(ConnectionConfig config) {
        this.config = config;
    }

    public ConnectionConfig getConfig() {
        return config;
    }

    public record ConnectionConfig(Paint color, double width, ConnectionRelationship desiredRelationship) {
    }

    public enum ConnectionRelationship {
        /*
        Determine the relationship of male-to-female connections.
        ONE_TO_ONE: 1 male to 1 female
        ONE_TO_MANY: 1 male to many females
        MANY_TO_ONE: Many males to 1 female
         */
        ONE_TO_ONE,
        ONE_TO_MANY,
        MANY_TO_ONE
    }
}
