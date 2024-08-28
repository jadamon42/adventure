package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.ConditionTranslator;
import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;

public class ItemCondition extends BasicNode implements ConditionTranslator {
    public ItemCondition() {
        NodeHeader header = new NodeHeader("Has Item", "Item Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.CONDITION);
        footer.addAttachment("Attach Item", ConnectionType.ITEM);
        setFooter(footer);
    }

    @Override
    public BooleanFunction<Player> getCondition() {
        BooleanFunction<Player> hasItem = player -> false;
        com.github.jadamon42.adventure.model.Item item = null;
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
}
