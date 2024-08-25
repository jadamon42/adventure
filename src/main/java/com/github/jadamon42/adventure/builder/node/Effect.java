package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;

public class Effect extends StoryNode {
    public Effect() {
        NodeHeader header = new NodeHeader("New Effect", "Effect");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher();
        setFooter(footer);
    }
}
