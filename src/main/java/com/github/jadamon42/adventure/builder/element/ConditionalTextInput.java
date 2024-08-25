package com.github.jadamon42.adventure.builder.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.util.UUID;

public class ConditionalTextInput extends HBox {
    private final String id;
    private final HBox leftIcons;
    private final ExpandableTextInput textInput;
    private EventHandler<Event> onDelete;

    public ConditionalTextInput(String promptText, boolean linkable) {
        id = UUID.randomUUID().toString();
        setId(id);
        leftIcons = new HBox();
        leftIcons.setPrefWidth(32);
        leftIcons.setAlignment(Pos.CENTER_LEFT);
        leftIcons.setSpacing(5);
        leftIcons.setPadding(new Insets(0, 0, 0, 5));
        AttachmentLink attachmentLink = new AttachmentLink(AttachmentLink.ObjectAttachmentType.CONDITION, AttachmentLink.GenderAttachmentType.FEMALE);
        leftIcons.getChildren().add(attachmentLink);
        getChildren().add(leftIcons);

        textInput = new ExpandableTextInput(promptText);
        getChildren().add(textInput);

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

    public boolean isDeletable() {
        return leftIcons.getChildren().stream()
                        .anyMatch(child -> child.getId().equals(id + "-delete"));
    }

    public void setDeletable(boolean deletable) {
        if (deletable) {
            addDeletableIcon();
        } else {
            removeDeletableIcon();
        }
    }

    public void setOnDelete(EventHandler<Event> onDelete) {
        this.onDelete = onDelete;
        leftIcons.getChildren().stream()
                .filter(child -> child.getId().equals(id + "-delete"))
                .findFirst()
                .ifPresent(delete -> delete.setOnMouseClicked(this.onDelete));
    }

    private void addDeletableIcon() {
        if (leftIcons.getChildren().stream().noneMatch(child -> child.getId().equals(id + "-delete"))) {
            FontAwesomeIconView delete = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            delete.setId(id + "-delete");
            delete.setFill(Paint.valueOf("lightgrey"));
            delete.setCursor(Cursor.HAND);
            leftIcons.getChildren().add(delete);
        }
    }

    private void removeDeletableIcon() {
        leftIcons.getChildren().removeIf(child -> child.getId().equals(id + "-delete"));
    }

    public void setPromptText(String promptText) {
        textInput.setPromptText(promptText);
    }
}
