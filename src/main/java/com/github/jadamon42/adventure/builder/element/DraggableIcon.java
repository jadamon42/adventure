package com.github.jadamon42.adventure.builder.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class DraggableIcon extends HBox {
    public DraggableIcon() {
        setAlignment(Pos.CENTER);
        FontAwesomeIconView draggableIcon = new FontAwesomeIconView(FontAwesomeIcon.BARS);
        draggableIcon.setScaleX(2.5);
        draggableIcon.setScaleY(0.8);
        draggableIcon.setFill(Paint.valueOf("lightgrey"));
        draggableIcon.setCursor(Cursor.CLOSED_HAND);
        getChildren().add(draggableIcon);
    }
}
