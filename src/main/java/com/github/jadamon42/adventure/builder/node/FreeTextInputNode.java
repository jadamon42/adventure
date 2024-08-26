package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.ConnectionType;

public class FreeTextInputNode extends StoryNode {
    public FreeTextInputNode() {
        NodeHeader header = new NodeHeader("Free Text Input", "Free Text Input Node");
        header.addNextNodeLink();
        setHeader(header);
        setGameMessageInput("Enter game message");
        NodeFooter footer = new NodeFooter();
        footer.addAttachment("Attach Logic", ConnectionType.HANDLER);
        setFooter(footer);
    }
}
