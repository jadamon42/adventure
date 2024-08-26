package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.ConnectionType;

public class Effect extends BasicNode {
    private com.github.jadamon42.adventure.model.Effect effect;
    private NodeHeader header;

    public Effect() {
        header = new NodeHeader("New Effect", "Effect");
        setHeader(header);
        header.setOnKeyTyped(event -> {
            effect = new com.github.jadamon42.adventure.model.Effect(header.getTitle());
        });
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.EFFECT);
        setFooter(footer);
        effect = new com.github.jadamon42.adventure.model.Effect(header.getTitle());
    }

    public com.github.jadamon42.adventure.model.Effect getEffectModel() {
        return effect;
    }
}
