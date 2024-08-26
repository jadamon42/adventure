package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.ConnectionType;

public class Item extends BasicNode {
    public Item() {
        NodeHeader header = new NodeHeader("New Item", "Item");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.ITEM);
        setFooter(footer);
    }
}
