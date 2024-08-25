package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;

public class EffectCondition extends StoryNode {
    public EffectCondition() {
        NodeHeader header = new NodeHeader("Has Effect", "Effect Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher();
        footer.addAttachment("Attach Effect");
        setFooter(footer);
    }
}
