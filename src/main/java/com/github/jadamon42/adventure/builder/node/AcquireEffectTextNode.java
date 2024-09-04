package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.common.util.ListHelper;

public class AcquireEffectTextNode extends BasicNode implements StoryNodeTranslator, VisitableNode {
    private final AttachmentLink effectLink;

    public AcquireEffectTextNode() {
        NodeHeader header = new NodeHeader("Acquire Effect", "Acquire Effect Text Node");
        header.addPreviousNodeConnection();
        header.setNextNodeConnection();
        setHeader(header);
        setGameMessageInput("Enter game message");
        NodeFooter footer = new NodeFooter();
        effectLink = footer.addAttachment("Attach Effect", ConnectionType.EFFECT);
        setFooter(footer);
    }

    @Override
    public com.github.jadamon42.adventure.common.node.AcquireEffectTextNode toStoryNode() {
        String text = getText();
        com.github.jadamon42.adventure.common.model.Effect effect = null;
        for (Node node : getAttachmentNodes()) {
            if (node instanceof Effect effectNode) {
                effect = effectNode.getEffectModel();
            }
        }
        if (effect == null) {
            throw new RuntimeException("Not connected to effect node");
        }
        var retval = new com.github.jadamon42.adventure.common.node.AcquireEffectTextNode(text, effect);
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

    public String getEffectConnectionId() {
        return ListHelper.getFirst(getFooter().getAttachmentConnectionIds());
    }

    public void setEffectConnection(ConnectionLine connectionLine) {
        effectLink.addConnection(connectionLine);
    }
}
