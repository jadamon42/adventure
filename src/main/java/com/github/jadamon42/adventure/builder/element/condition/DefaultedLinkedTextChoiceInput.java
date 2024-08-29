package com.github.jadamon42.adventure.builder.element.condition;

import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.builder.node.Node;
import com.github.jadamon42.adventure.node.LinkedTextChoice;
import com.github.jadamon42.adventure.node.StoryNode;

public class DefaultedLinkedTextChoiceInput extends LinkedTextChoiceInput {
    public DefaultedLinkedTextChoiceInput(String promptText) {
        super(promptText, true);
    }

    @Override
    public LinkedTextChoice toConditionalText() {
        Node nextNode = nodeLink.getLinkedNode();
        StoryNode next = null;
        if (nextNode instanceof StoryNodeTranslator nextStoryNode) {
            next = nextStoryNode.toStoryNode();
        }
        return new LinkedTextChoice(getText(), next);
    }

    @Override
    public boolean isDefaulted() {
        return true;
    }
}
