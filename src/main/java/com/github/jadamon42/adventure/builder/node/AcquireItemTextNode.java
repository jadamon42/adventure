package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.ConnectionType;
import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;

public class AcquireItemTextNode extends BasicNode  implements StoryNodeTranslator {
    public AcquireItemTextNode() {
        NodeHeader header = new NodeHeader("Acquire Item", "Acquire Item Text Node");
        header.addPreviousNodeLink();
        header.addNextNodeLink();
        setHeader(header);
        setGameMessageInput("Enter game message");
        NodeFooter footer = new NodeFooter();
        footer.addAttachment("Attach Item", ConnectionType.ITEM);
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
}
