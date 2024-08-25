package com.github.jadamon42.adventure.builder.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

class NodeLink extends HBox {
    public NodeLink() {
        FontAwesomeIconView nodeLinkIcon = new FontAwesomeIconView(FontAwesomeIcon.CARET_SQUARE_ALT_RIGHT);
        nodeLinkIcon.setFill(Paint.valueOf("lightgrey"));
        nodeLinkIcon.setGlyphSize(25);
        setMinWidth(HBox.USE_PREF_SIZE);
        setMinHeight(HBox.USE_PREF_SIZE);
        setMaxWidth(HBox.USE_PREF_SIZE);
        setMaxHeight(HBox.USE_PREF_SIZE);
        getChildren().add(nodeLinkIcon);
    }
}
