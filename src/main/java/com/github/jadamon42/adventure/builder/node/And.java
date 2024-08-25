package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;

public class And extends StoryNode {
    public And() {
        NodeHeader header = new NodeHeader("And", "And Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher();
        footer.addAttachment("Condition 1");
        footer.addAttachment("Condition 2");
        setFooter(footer);
    }
}
