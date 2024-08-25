package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.AttachmentLink;
import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;

public class ItemCondition extends StoryNode {
    public ItemCondition() {
        NodeHeader header = new NodeHeader("Has Item", "Item Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(AttachmentLink.ObjectAttachmentType.CONDITION);
        footer.addAttachment("Attach Item", AttachmentLink.ObjectAttachmentType.ITEM);
        setFooter(footer);
    }
}
