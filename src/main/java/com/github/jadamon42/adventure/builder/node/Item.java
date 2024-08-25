package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.AttachmentLink;
import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;

public class Item extends StoryNode {
    public Item() {
        NodeHeader header = new NodeHeader("New Item", "Item");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(AttachmentLink.ObjectAttachmentType.ITEM);
        setFooter(footer);
    }
}
