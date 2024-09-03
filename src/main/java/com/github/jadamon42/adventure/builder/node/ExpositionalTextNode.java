package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.NodeVisitor;
import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.builder.element.VisitableNode;

public class ExpositionalTextNode extends BasicNode implements StoryNodeTranslator, VisitableNode {
    public ExpositionalTextNode() {
        NodeHeader header = new NodeHeader("Expositional Text", "Expositional Text Node");
        header.addPreviousNodeConnection();
        header.setNextNodeConnection();
        setHeader(header);
        setGameMessageInput("Enter game message");
    }

    @Override
    public com.github.jadamon42.adventure.common.node.ExpositionalTextNode toStoryNode() {
        var retval = new com.github.jadamon42.adventure.common.node.ExpositionalTextNode(getText());
        Node nextNode = getNextNode();
        if (nextNode instanceof StoryNodeTranslator nextStoryNode) {
            retval.then(nextStoryNode.toStoryNode());
        }
        return retval;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
