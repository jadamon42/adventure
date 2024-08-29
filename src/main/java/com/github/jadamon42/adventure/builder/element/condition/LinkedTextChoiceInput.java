package com.github.jadamon42.adventure.builder.element.condition;

import com.github.jadamon42.adventure.builder.element.connection.ConnectionGender;
import com.github.jadamon42.adventure.builder.element.NodeLink;
import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.node.Node;
import com.github.jadamon42.adventure.node.LinkedTextChoice;
import com.github.jadamon42.adventure.node.StoryNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class LinkedTextChoiceInput extends AbstractConditionalTextInput implements ConditionalTextTranslator<LinkedTextChoice> {
    protected final NodeLink nodeLink = new NodeLink(ConnectionGender.MALE);

    public LinkedTextChoiceInput(String promptText) {
        super(promptText);
        addRightIcon();
    }

    public LinkedTextChoiceInput(String promptText, boolean defaulted) {
        super(promptText, defaulted);
        addRightIcon();
    }

    private void addRightIcon() {
        HBox rightIcons = new HBox();
        rightIcons.setPrefWidth(32);
        rightIcons.setAlignment(Pos.CENTER_RIGHT);
        rightIcons.setSpacing(5);
        rightIcons.setPadding(new Insets(0, 5, 0, 0));
        rightIcons.getChildren().add(nodeLink);
        getChildren().add(rightIcons);
    }

    @Override
    public LinkedTextChoice toConditionalText() {
        Node nextNode = nodeLink.getLinkedNode();
        StoryNode next = null;
        if (nextNode instanceof StoryNodeTranslator nextStoryNode) {
            next = nextStoryNode.toStoryNode();
        }
        return new LinkedTextChoice(getText(), next, getCondition());
    }

    @Override
    public String getNextConnectionId() {
        return getFirst(nodeLink.getConnectionIds());
    }

    public void setNextNodeConnection(ConnectionLine connection) {
        nodeLink.addConnection(connection);
    }

    @Override
    public void onParentDragged() {
        notifyConditionLinkOfParentDrag();
        nodeLink.onParentDragged();
    }
}
