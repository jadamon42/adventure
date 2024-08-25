package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.AttachmentLink;
import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;

public class Or extends StoryNode {
    public Or() {
        NodeHeader header = new NodeHeader("Or", "Or Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(AttachmentLink.ObjectAttachmentType.CONDITION);
        footer.addAttachment("Condition 1", AttachmentLink.ObjectAttachmentType.CONDITION);
        footer.addAttachment("Condition 2", AttachmentLink.ObjectAttachmentType.CONDITION);
        setFooter(footer);
    }
}
