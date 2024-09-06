package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.common.state.PlayerDelta;
import com.github.jadamon42.adventure.common.util.ListUtils;
import com.github.jadamon42.adventure.common.util.PlayerDeltaBiFunction;

public class FreeTextInputNode extends BasicNode implements StoryNodeTranslator, VisitableNode {
    private final AttachmentLink inputHandlerLink;
    private com.github.jadamon42.adventure.common.node.FreeTextInputNode storyNode;

    public FreeTextInputNode() {
        NodeHeader header = new NodeHeader("Free Text Input", "Free Text Input Node");
        header.addPreviousNodeConnection();
        header.setNextNodeConnection();
        setHeader(header);
        setGameMessageInput("Enter game message");
        NodeFooter footer = new NodeFooter();
        inputHandlerLink = footer.addAttachment("Attach Logic", ConnectionType.HANDLER);
        setFooter(footer);
    }

    public static String getDescription() {
        return "Allow the player to enter free text. Use an input handler to process the text.";
    }

    @Override
    public com.github.jadamon42.adventure.common.node.FreeTextInputNode toStoryNode() {
        if (storyNode == null) {
            storyNode = buildStoryNode();
        }
        return storyNode;
    }

    @Override
    public void clearCachedStoryNode() {
        storyNode = null;
    }

    private com.github.jadamon42.adventure.common.node.FreeTextInputNode buildStoryNode() {
        PlayerDeltaBiFunction<Object> handler = (player, obj) -> new PlayerDelta();
        Node node = ListUtils.getFirst(getAttachmentNodes());
        if (node instanceof InputHandler handlerNode) {
            handler = handlerNode.getHandler();
        }
        var retval = new com.github.jadamon42.adventure.common.node.FreeTextInputNode(getText(), handler);
        Node nextNode = getNextNode();
        if (nextNode instanceof StoryNodeTranslator nextStoryNode) {
            retval.then(nextStoryNode.toStoryNode());
        }
        return retval;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public String getInputHandlerConnectionId() {
        return ListUtils.getFirst(getFooter().getAttachmentConnectionIds());
    }

    public void setInputHandlerConnection(ConnectionLine connectionLine) {
        inputHandlerLink.addConnection(connectionLine);
    }
}
