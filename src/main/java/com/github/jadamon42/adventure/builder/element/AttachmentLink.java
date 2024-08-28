package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.element.connection.ConnectionGender;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionPoint;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.paint.Paint;

public class AttachmentLink extends ConnectionPoint {

    private final ConnectionType connectionType;

    public AttachmentLink(ConnectionType connectionType, ConnectionGender connectionGender) {
        super(connectionGender);
        this.connectionType = connectionType;
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE_THIN);
        icon.setFill(Paint.valueOf("lightgrey"));
        icon.setGlyphSize(12);
        getChildren().add(icon);
    }

    public ConnectionType getType() {
        return connectionType;
    }
}
