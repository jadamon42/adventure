package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;

import java.util.List;

public class Item extends BasicNode implements VisitableNode {
    private com.github.jadamon42.adventure.common.model.Item item;
    private final AttachmentLink itemLink;

    public Item() {
        NodeHeader header = new NodeHeader("New Item", "Item");
        setHeader(header);
        header.setChildOnKeyTyped(event -> item = new com.github.jadamon42.adventure.common.model.Item(getTitle()));
        NodeFooter footer = new NodeFooter();
        itemLink = footer.addAttacher(ConnectionType.ITEM);
        setFooter(footer);
        item = new com.github.jadamon42.adventure.common.model.Item(getTitle());
    }

    public static String getDescription() {
        return "An item that can be acquired by the player, or used to evaluate conditions.";
    }

    public com.github.jadamon42.adventure.common.model.Item getItemModel() {
        return item;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public List<String> getItemConnectionIds() {
        return getFooter().getAttacherConnectionIds();
    }

    public void addItemConnection(ConnectionLine connectionLine) {
        itemLink.addConnection(connectionLine);
    }
}
