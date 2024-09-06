package com.github.jadamon42.adventure.runner.ui.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ReplayButton extends HBox {
    public ReplayButton(EventHandler<MouseEvent> eventHandler) {
        getStyleClass().add("replay-button");
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ROTATE_LEFT);
        getChildren().add(icon);

        setOnMouseClicked(eventHandler);
        setVisible(false);
    }
}
