package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;

public class AcquireItemTextNode extends BasicNode  implements StoryNodeTranslator, VisitableNode {
    private final AttachmentLink itemLink;

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

    @Override
    public com.github.jadamon42.adventure.common.node.AcquireItemTextNode toStoryNode() {
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
        return getFirst(getFooter().getAttachmentConnectionIds());
    }

    public void setItemConnection(ConnectionLine connectionLine) {
        itemLink.addConnection(connectionLine);
    }
}
