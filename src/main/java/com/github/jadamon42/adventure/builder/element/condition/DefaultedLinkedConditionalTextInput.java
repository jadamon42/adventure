package com.github.jadamon42.adventure.builder.element.condition;

import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.builder.node.Node;
import com.github.jadamon42.adventure.node.LinkedConditionalText;
import com.github.jadamon42.adventure.node.StoryNode;

public class DefaultedLinkedConditionalTextInput extends LinkedConditionalTextInput {
    public DefaultedLinkedConditionalTextInput(String promptText) {
        super(promptText, true);
    }

    @Override
    public LinkedConditionalText toConditionalText() {
        Node nextNode = nodeLink.getLinkedNode();
        StoryNode next = null;
        if (nextNode instanceof StoryNodeTranslator nextStoryNode) {
            next = nextStoryNode.toStoryNode();
        }
        return new LinkedConditionalText(getText(), next);
    }

    @Override
    public boolean isDefaulted() {
        return true;
    }
}
