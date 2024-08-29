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
    public com.github.jadamon42.adventure.node.AcquireEffectTextNode toStoryNode() {
        String text = getText();
        com.github.jadamon42.adventure.model.Effect effect = null;
        for (Node node : getAttachmentNodes()) {
            if (node instanceof Effect effectNode) {
                effect = effectNode.getEffectModel();
            }
        }
        if (effect == null) {
            throw new RuntimeException("Not connected to effect node");
        }
        var retval = new com.github.jadamon42.adventure.node.AcquireEffectTextNode(text, effect);
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
