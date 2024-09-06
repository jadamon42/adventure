package com.github.jadamon42.adventure.runner.ui.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class TypingIndicator extends HBox {
    public TypingIndicator() {
        getStyleClass().add("typing-indicator-container");
        FontAwesomeIconView dot1 = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
        FontAwesomeIconView dot2 = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
        FontAwesomeIconView dot3 = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
        getChildren().addAll(dot1, dot2, dot3);

        createBounceAnimation(dot1, 0).play();
        createBounceAnimation(dot2, 200).play();
        createBounceAnimation(dot3, 400).play();
    }

    private Timeline createBounceAnimation(FontAwesomeIconView dot, int delay) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(dot.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(dot.translateYProperty(), -10)),
                new KeyFrame(Duration.millis(400), new KeyValue(dot.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(1000), new KeyValue(dot.translateYProperty(), 0))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setDelay(Duration.millis(delay));
        return timeline;
    }
}
