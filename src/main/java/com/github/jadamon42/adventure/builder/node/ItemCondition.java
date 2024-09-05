package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.util.BooleanFunction;
import com.github.jadamon42.adventure.common.util.ListUtils;

import java.util.List;

public class ItemCondition extends BasicNode implements ConditionTranslator, VisitableNode {
    private final AttachmentLink itemLink;
    private final AttachmentLink conditionLink;

    public ItemCondition() {
        NodeHeader header = new NodeHeader("Has Item", "Item Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        conditionLink = footer.addAttacher(ConnectionType.CONDITION);
        itemLink = footer.addAttachment("Attach Item", ConnectionType.ITEM);
        setFooter(footer);
    }

    @Override
    public BooleanFunction<Player> getCondition() {
        BooleanFunction<Player> hasItem = player -> false;
        com.github.jadamon42.adventure.common.model.Item item = null;
        for (Node node : getAttachmentNodes()) {
            if (node instanceof Item itemNode) {
                item = itemNode.getItemModel();
            }
        }
        if (item != null) {
            String itemName = item.getName();
            hasItem = player -> player.hasItem(itemName);
        }
        return hasItem;
    }

    public static String getDescription() {
        return "Check if the player has a specific item.";
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public String getItemConnectionId() {
        return ListUtils.getFirst(getFooter().getAttachmentConnectionIds());
    }

    public List<String> getConditionConnectionIds() {
        return getFooter().getAttacherConnectionIds();
    }

    public void setItemConnection(ConnectionLine connectionLine) {
        itemLink.addConnection(connectionLine);
    }

    public void addConditionConnection(ConnectionLine connectionLine) {
        conditionLink.addConnection(connectionLine);
    }
}
