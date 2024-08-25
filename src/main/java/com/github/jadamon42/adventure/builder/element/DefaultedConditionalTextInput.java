package com.github.jadamon42.adventure.builder.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;

public class DefaultedConditionalTextInput extends HBox {
    public DefaultedConditionalTextInput(String promptText, boolean linkable) {
//        super(promptText);

//        if (linkable) {
//            FontAwesomeIconView linkNextNodeIcon = new FontAwesomeIconView(FontAwesomeIcon.CARET_SQUARE_ALT_RIGHT);
//            linkNextNodeIcon.setFill(Paint.valueOf("lightgrey"));
//            linkNextNodeIcon.setGlyphSize(25);
//            getChildren().add(linkNextNodeIcon);
//        }



        HBox leftIcons = new HBox();
        leftIcons.setPrefWidth(32);
//        leftIcons.setMinWidth(15);
        leftIcons.setAlignment(Pos.CENTER_LEFT);
        leftIcons.setSpacing(5);
        leftIcons.setPadding(new Insets(0, 0, 0, 5));
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        leftIcons.getChildren().addFirst(spacer);
        getChildren().add(leftIcons);

        getChildren().add(new ExpandableTextInput(promptText));

        if (linkable) {
            HBox rightIcons = new HBox();
            rightIcons.setPrefWidth(32);
            rightIcons.setAlignment(Pos.CENTER_RIGHT);
            rightIcons.setSpacing(5);
            rightIcons.setPadding(new Insets(0, 5, 0, 0));
            FontAwesomeIconView linkNextNodeIcon = new FontAwesomeIconView(FontAwesomeIcon.CARET_SQUARE_ALT_RIGHT);
            linkNextNodeIcon.setFill(Paint.valueOf("lightgrey"));
            linkNextNodeIcon.setGlyphSize(25);
            rightIcons.getChildren().add(linkNextNodeIcon);
            getChildren().add(rightIcons);
        }
    }
}
