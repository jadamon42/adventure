package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.common.util.ListUtils;

public class AcquireItemTextNode extends BasicNode  implements StoryNodeTranslator, VisitableNode {
    private final AttachmentLink itemLink;
    private com.github.jadamon42.adventure.common.node.AcquireItemTextNode storyNode;

    public AcquireItemTextNode() {
        NodeHeader header = new NodeHeader("Acquire Item", "Acquire Item Text Node");
        header.addPreviousNodeConnection();
        header.setNextNodeConnection();
        setHeader(header);
        setGameMessageInput("Enter game message");
        NodeFooter footer = new NodeFooter();
        itemLink = footer.addAttachment("Attach Item", ConnectionType.ITEM);
        setFooter(footer);
    }

    public static String getDescription() {
        return "Add an item to the player's inventory.";
    }

    @Override
    public com.github.jadamon42.adventure.common.node.AcquireItemTextNode toStoryNode() {
        if (storyNode == null) {
            storyNode = buildStoryNode();
        }
        return storyNode;
    }

    @Override
    public void clearCachedStoryNode() {
        storyNode = null;
    }

    private com.github.jadamon42.adventure.common.node.AcquireItemTextNode buildStoryNode() {
        String text = getText();
        com.github.jadamon42.adventure.common.model.Item item = null;
        for (Node node : getAttachmentNodes()) {
            if (node instanceof Item effectNode) {
                item = effectNode.getItemModel();
            }
        }
        if (item == null) {
            throw new RuntimeException("Not connected to item node");
        }
        var retval = new com.github.jadamon42.adventure.common.node.AcquireItemTextNode(text, item);
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

    public String getItemConnectionId() {
        return ListUtils.getFirst(getFooter().getAttachmentConnectionIds());
    }

    public void setItemConnection(ConnectionLine connectionLine) {
        itemLink.addConnection(connectionLine);
    }
}
