package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.node.StoryNode;

public class ExpositionalTextNode extends BasicNode implements StoryNodeTranslator {
    public ExpositionalTextNode() {
        NodeHeader header = new NodeHeader("Expositional Text", "Expositional Text Node");
        header.addPreviousNodeLink();
        header.addNextNodeLink();
        setHeader(header);
        setGameMessageInput("Enter game message");
    }

    @Override
    public com.github.jadamon42.adventure.node.ExpositionalTextNode toStoryNode() {
        var retval = new com.github.jadamon42.adventure.node.ExpositionalTextNode(getText());
        Node nextNode = getNextNode();
        if (nextNode instanceof StoryNodeTranslator nextStoryNode) {
            retval.then(nextStoryNode.toStoryNode());
        }
        return retval;
    }
}
