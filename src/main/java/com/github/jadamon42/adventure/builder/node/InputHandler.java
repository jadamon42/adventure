package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;

public class InputHandler extends BasicNode {
    public InputHandler() {
        NodeHeader header = new NodeHeader("Handle Input", "Input Handler");
        setHeader(header);
        SubTypeSelector selector = new SubTypeSelector(
                "Set Name",
                event -> {}
        );
        setSubTypeSelector(selector);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.HANDLER);
        setFooter(footer);
    }
}
