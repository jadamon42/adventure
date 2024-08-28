package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;

public class Item extends BasicNode {
    private com.github.jadamon42.adventure.model.Item item;

    public Item() {
        NodeHeader header = new NodeHeader("New Item", "Item");
        setHeader(header);
        header.setChildOnKeyTyped(event -> item = new com.github.jadamon42.adventure.model.Item(header.getTitle()));
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.ITEM);
        setFooter(footer);
        item = new com.github.jadamon42.adventure.model.Item(header.getTitle());
    }

    public com.github.jadamon42.adventure.model.Item getItemModel() {
        return item;
    }
}
