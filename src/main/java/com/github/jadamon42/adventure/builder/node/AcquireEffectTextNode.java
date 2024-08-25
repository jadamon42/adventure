package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.AttachmentLink;
import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;

public class AcquireEffectTextNode extends StoryNode {
    public AcquireEffectTextNode() {
        NodeHeader header = new NodeHeader("Acquire Effect", "Acquire Effect Text Node");
        header.addPreviousNodeLink();
        header.addNextNodeLink();
        setHeader(header);
        setGameMessageInput("Enter game message");
        NodeFooter footer = new NodeFooter();
        footer.addAttachment("Attach Effect", AttachmentLink.ObjectAttachmentType.EFFECT);
        setFooter(footer);
    }
}
