package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.condition.ConditionalTextInputContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicNode extends Node {
    private ExpandableTextInput textInput;
    private NodeHeader header;
    private NodeFooter footer;

    public BasicNode() {
        getStyleClass().add("node");
        getChildren().add(new DraggableIcon());
    }

    public String getText() {
        String text = null;
        if (textInput != null) {
            text = textInput.getText();
        }
        return text;
    }

    public void setHeader(NodeHeader header) {
        this.header = header;
        getChildren().removeIf(element -> element instanceof NodeHeader);
        getChildren().add(header);
        orderChildren();
    }

    public NodeHeader getHeader() {
        return header;
    }

    public void setSubTypeSelector(SubTypeSelector selector) {
        getChildren().removeIf(element -> element instanceof SubTypeSelector);
        getChildren().add(selector);
        orderChildren();
    }

    public void setGameMessageInput(String promptText) {
        if (textInput == null) {
            textInput = new ExpandableTextInput(promptText);
            getChildren().add(textInput);
        } else {
            textInput.setPromptText(promptText);
        }
        orderChildren();
    }

    public void setConditionals(ConditionalTextInputContainer options) {
        getChildren().removeIf(element -> element instanceof ConditionalTextInputContainer);
        getChildren().add(options);
        orderChildren();
    }

    public void setFooter(NodeFooter footer) {
        this.footer = footer;
        getChildren().removeIf(element -> element instanceof NodeFooter);
        getChildren().add(footer);
        orderChildren();
    }

    public NodeFooter getFooter() {
        return footer;
    }

    public Node getNextNode() {
        return header.getNextNode();
    }

    public List<Node> getAttachmentNodes() {
        return footer.getAttachmentNodes();
    }

    public List<Node> getAttacherNodes() {
        return footer.getAttacherNodes();
    }

    private void orderChildren() {
        List<javafx.scene.Node> sortedChildren = new ArrayList<>(getChildren());
        sortedChildren.sort((node1, node2) -> {
            if (node1 instanceof DraggableIcon) {
                return -1;
            } else if (node2 instanceof DraggableIcon) {
                return 1;
            } else if (node1 instanceof NodeHeader) {
                return -1;
            } else if (node2 instanceof NodeHeader) {
                return 1;
            } else if (node1 instanceof SubTypeSelector) {
                return -1;
            } else if (node2 instanceof SubTypeSelector) {
                return 1;
            } else if (node1 instanceof ExpandableTextInput) {
                return -1;
            } else if (node2 instanceof ExpandableTextInput) {
                return 1;
            } else if (node1 instanceof ConditionalTextInputContainer) {
                return -1;
            } else if (node2 instanceof ConditionalTextInputContainer) {
                return 1;
            } else if (node1 instanceof NodeFooter) {
                return -1;
            } else if (node2 instanceof NodeFooter) {
                return 1;
            } else {
                return 0;
            }
        });
        getChildren().clear();
        getChildren().addAll(sortedChildren);
    }
}
