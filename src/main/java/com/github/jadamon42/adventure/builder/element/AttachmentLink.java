package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.element.connection.ConnectionGender;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionPoint;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.common.util.StringUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class AttachmentLink extends ConnectionPoint {

    private final ConnectionType connectionType;

    public AttachmentLink(ConnectionType connectionType, ConnectionGender connectionGender) {
        super(connectionGender);
        this.connectionType = connectionType;
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE_THIN);
        icon.setFill(Paint.valueOf("lightgrey"));
        icon.setGlyphSize(12);
        getChildren().add(icon);


        String description;
        if (connectionGender == ConnectionGender.MALE) {
            description= StringUtils.softWrap(
                    StringUtils.capitalize(
                        connectionType.getConfig().displayableType() + " attachment"),
                    50);
        } else {
            description = StringUtils.softWrap(
                    "Attach " + connectionType.getConfig().displayableType(),
                    50);
        }
        Tooltip tooltip = new Tooltip(description);
        tooltip.setShowDuration(Duration.INDEFINITE);
        Tooltip.install(this, tooltip);
    }

    public ConnectionType getType() {
        return connectionType;
    }
}
