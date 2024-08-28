package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.ConnectionType;
import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.model.PlayerDelta;
import com.github.jadamon42.adventure.node.StoryNode;
import com.github.jadamon42.adventure.util.PlayerDeltaBiFunction;

public class FreeTextInputNode extends BasicNode implements StoryNodeTranslator {
    public FreeTextInputNode() {
        NodeHeader header = new NodeHeader("Free Text Input", "Free Text Input Node");
        header.addPreviousNodeLink();
        header.addNextNodeLink();
        setHeader(header);
        setGameMessageInput("Enter game message");
        NodeFooter footer = new NodeFooter();
        footer.addAttachment("Attach Logic", ConnectionType.HANDLER);
        setFooter(footer);
    }

    @Override
    public com.github.jadamon42.adventure.node.FreeTextInputNode toStoryNode() {
        PlayerDeltaBiFunction<Player, Object> handler = (player, obj) -> new PlayerDelta();
        Node node = getAttachmentNodes().getFirst();
        if (node instanceof InputHandler handlerNode) {
            handler = handlerNode.getHandler();
        }
        var retval = new com.github.jadamon42.adventure.node.FreeTextInputNode(getText(), handler);
        Node nextNode = getNextNode();
        if (nextNode instanceof StoryNodeTranslator nextStoryNode) {
            retval.then(nextStoryNode.toStoryNode());
        }
        return retval;
    }
}
