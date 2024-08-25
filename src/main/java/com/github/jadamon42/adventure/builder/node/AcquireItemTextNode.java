package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.AttachmentLink;
import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;

public class AcquireItemTextNode extends StoryNode {
    public AcquireItemTextNode() {
        NodeHeader header = new NodeHeader("Acquire Item", "Acquire Item Text Node");
        header.addPreviousNodeLink();
        header.addNextNodeLink();
        setHeader(header);
        setGameMessageInput("Enter game message");
        NodeFooter footer = new NodeFooter();
        footer.addAttachment("Attach Item", AttachmentLink.ObjectAttachmentType.ITEM);
        setFooter(footer);
    }
}
