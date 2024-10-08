package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.NodeVisitor;
import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.builder.element.VisitableNode;

public class ExpositionalTextNode extends BasicNode implements StoryNodeTranslator, VisitableNode {
    private com.github.jadamon42.adventure.common.node.ExpositionalTextNode storyNode;

    public ExpositionalTextNode() {
        NodeHeader header = new NodeHeader("Expositional Text", "Expositional Text Node");
        header.addPreviousNodeConnection();
        header.setNextNodeConnection();
        setHeader(header);
        setGameMessageInput("Enter game message");
    }

    public static String getDescription() {
        return "Display a message to the player to advance the story.";
    }

    @Override
    public com.github.jadamon42.adventure.common.node.ExpositionalTextNode toStoryNode() {
        if (storyNode == null) {
            storyNode = buildStoryNode();
        }
        return storyNode;
    }

    @Override
    public void clearCachedStoryNode() {
        storyNode = null;
    }

    private com.github.jadamon42.adventure.common.node.ExpositionalTextNode buildStoryNode() {
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
