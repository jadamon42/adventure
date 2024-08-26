package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeHeader;

public class ExpositionalTextNode extends BasicNode {
    public ExpositionalTextNode() {
        NodeHeader header = new NodeHeader("Expositional Text", "Expositional Text Node");
        header.addPreviousNodeLink();
        header.addNextNodeLink();
        setHeader(header);
        setGameMessageInput("Enter game message");
    }
}
