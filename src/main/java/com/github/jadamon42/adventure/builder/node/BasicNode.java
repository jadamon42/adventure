package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.condition.ConditionalTextInputContainer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BasicNode extends Node {
    private ExpandableTextInput textInput;
    private NodeHeader header;
    private NodeFooter footer;
    private SubtypeSelector subTypeSelector;

    public BasicNode() {
        getStyleClass().add("node");
        getChildren().add(new DraggableIcon());
        this.textInput = null;
        this.header = null;
        this.footer = null;
        this.subTypeSelector = null;
    }

    public void setText(String text) {
        if (textInput != null) {
            textInput.setText(text);
        }
    }

    public String getText() {
        String text = null;
        if (textInput != null) {
            text = textInput.getText();
        }
        return text;
    }

    public void setTitle(String title) {
        header.setTitle(title);
    }

    @Override
    public String getTitle() {
        return header.getTitle();
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

    public void setSubtypeSelector(SubtypeSelector selector) {
        this.subTypeSelector = selector;
        getChildren().removeIf(element -> element instanceof SubtypeSelector);
        getChildren().add(selector);
        orderChildren();
    }

    public SubtypeSelector getSubtypeSelector() {
        return subTypeSelector;
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

    public void removeGameMessageInput() {
        if (textInput != null) {
            getChildren().remove(textInput);
            textInput = null;
        }
    }

    public void setOnTextChange(EventHandler<ActionEvent> eventHandler) {
        if (textInput != null) {
            textInput.setOnTextChange(eventHandler);
        }
    }

    @Override
    public boolean isValid() {
        return textInput == null || textInput.isValid();
    }

    public void setInputValidityCheck(Predicate<String> validityCheck) {
        if (textInput != null) {
            textInput.setValidityCheck(validityCheck);
        }
    }

    public void setInputInvalidTooltip(Tooltip invalidTooltip) {
        if (textInput != null) {
            textInput.setInvalidTooltip(invalidTooltip);
        }
    }

    public void checkValidityOnInput() {
        if (textInput != null) {
            textInput.checkValidity();
        }
    }

    public void setConditionals(ConditionalTextInputContainer<?> options) {
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

    public String getNextNodeConnectionId() {
        return header.getNextNodeConnectionId();
    }

    public List<String> getPreviousNodeConnectionIds() {
        return header.getPreviousNodeConnectionIds();
    }

    public List<Node> getAttachmentNodes() {
        return footer.getAttachmentNodes();
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
            } else if (node1 instanceof SubtypeSelector) {
                return -1;
            } else if (node2 instanceof SubtypeSelector) {
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
