package com.github.jadamon42.adventure.builder.element.condition;

import com.github.jadamon42.adventure.builder.element.AttachmentLink;
import com.github.jadamon42.adventure.builder.element.ConnectionGender;
import com.github.jadamon42.adventure.builder.element.ConnectionType;
import com.github.jadamon42.adventure.builder.element.ExpandableTextInput;
import com.github.jadamon42.adventure.builder.node.ConditionTranslator;
import com.github.jadamon42.adventure.builder.node.Node;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;

import java.util.UUID;

public abstract class AbstractConditionalTextInput extends HBox {
    private final HBox leftIcons;
    private final ExpandableTextInput textInput;
    private EventHandler<Event> onDelete;
    private AttachmentLink conditionLink;

    public AbstractConditionalTextInput(String promptText) {
        setId(UUID.randomUUID().toString());
        leftIcons = new HBox();
        leftIcons.setPrefWidth(32);
        leftIcons.setAlignment(Pos.CENTER_LEFT);
        leftIcons.setSpacing(5);
        leftIcons.setPadding(new Insets(0, 0, 0, 5));
        conditionLink = new AttachmentLink(ConnectionType.CONDITION, ConnectionGender.FEMALE);
        leftIcons.getChildren().add(conditionLink);
        getChildren().add(leftIcons);

        textInput = new ExpandableTextInput(promptText);
        getChildren().add(textInput);
    }

    public AbstractConditionalTextInput(String promptText, boolean defaulted) {
        setId(UUID.randomUUID().toString());
        leftIcons = new HBox();
        leftIcons.setPrefWidth(32);
        leftIcons.setAlignment(Pos.CENTER_LEFT);
        leftIcons.setSpacing(5);
        leftIcons.setPadding(new Insets(0, 0, 0, 5));
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        leftIcons.getChildren().addFirst(spacer);
        getChildren().add(leftIcons);

        textInput = new ExpandableTextInput(promptText);
        getChildren().add(textInput);
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
                .filter(child -> child.getId().equals(getId() + "-delete"))
                .findFirst()
                .ifPresent(delete -> delete.setOnMouseClicked(this.onDelete));
    }

    private void addDeletableIcon() {
        if (leftIcons.getChildren().stream().noneMatch(child -> child.getId().equals(getId() + "-delete"))) {
            FontAwesomeIconView delete = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            delete.setId(getId() + "-delete");
            delete.setFill(Paint.valueOf("lightgrey"));
            delete.setCursor(Cursor.HAND);
            leftIcons.getChildren().add(delete);
        }
    }

    private void removeDeletableIcon() {
        leftIcons.getChildren().removeIf(child -> child.getId().equals(getId() + "-delete"));
    }

    public void setPromptText(String promptText) {
        textInput.setPromptText(promptText);
    }

    public String getText() {
        return textInput.getText();
    }

    public BooleanFunction<Player> getCondition() {
        BooleanFunction<Player> condition = player -> true;
        Node connectedNode = conditionLink.getConnectedNodes().getFirst();
        if (connectedNode instanceof ConditionTranslator conditionNode) {
            condition = conditionNode.getCondition();
        }
        return condition;
    }
}
