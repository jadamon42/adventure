package com.github.jadamon42.adventure.builder.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.paint.Paint;

public class NodeLink extends ConnectionPoint {
    public NodeLink(ConnectionGender gender) {
        super(gender);
        FontAwesomeIconView nodeLinkIcon = new FontAwesomeIconView(FontAwesomeIcon.CARET_SQUARE_ALT_RIGHT);
        nodeLinkIcon.setFill(Paint.valueOf("lightgrey"));
        nodeLinkIcon.setGlyphSize(25);
        getChildren().add(nodeLinkIcon);
    }

    @Override
    public ConnectionType getType() {
        return ConnectionType.NODE;
    }
}
