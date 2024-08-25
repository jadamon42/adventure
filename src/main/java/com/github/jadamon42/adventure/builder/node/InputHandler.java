package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.SubTypeSelector;

public class InputHandler extends StoryNode {
    public InputHandler() {
        NodeHeader header = new NodeHeader("Handle Input", "Input Handler");
        setHeader(header);
        SubTypeSelector selector = new SubTypeSelector(
                "Set Name",
                event -> {}
        );
        setSubTypeSelector(selector);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher();
        setFooter(footer);
    }
}
