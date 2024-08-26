package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.ConnectionType;

public class EffectCondition extends BasicNode {
    public EffectCondition() {
        NodeHeader header = new NodeHeader("Has Effect", "Effect Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.CONDITION);
        footer.addAttachment("Attach Effect", ConnectionType.EFFECT);
        setFooter(footer);
    }
}
