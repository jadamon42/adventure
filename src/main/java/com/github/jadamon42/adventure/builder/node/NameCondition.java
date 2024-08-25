package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.SubTypeSelector;

public class NameCondition extends StoryNode {
    public NameCondition() {
        NodeHeader header = new NodeHeader("Has Name", "Name Condition");
        setHeader(header);

        setGameMessageInput("");

        SubTypeSelector selector = new SubTypeSelector(
                "Name Equals",
                event -> setGameMessageInput("Player Name")
        );
        selector.addOption(
                "Name Contains",
                event -> setGameMessageInput("Substring")
        );
        selector.addOption(
                "Name Has Length",
                event -> setGameMessageInput("Length")
        );
        setSubTypeSelector(selector);

        NodeFooter footer = new NodeFooter();
        footer.addAttacher();
        setFooter(footer);
    }
}
