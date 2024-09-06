package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.NodeVisitor;
import com.github.jadamon42.adventure.builder.element.StoryNodeTranslator;
import com.github.jadamon42.adventure.builder.element.VisitableNode;
import com.github.jadamon42.adventure.common.util.StringUtils;

import java.time.Duration;

public class WaitNode extends BasicNode implements StoryNodeTranslator, VisitableNode {
    private com.github.jadamon42.adventure.common.node.WaitNode storyNode;

    public WaitNode() {
        NodeHeader header = new NodeHeader("Wait", "Wait Node");
        header.addPreviousNodeConnection();
        header.setNextNodeConnection();
        setHeader(header);
        setGameMessageInput("Duration (in seconds)");
        setInputValidityCheck(StringUtils::isNumber);
    }

    public static String getDescription() {
        return "Pause the story for a set amount of time.";
    }

    public Duration getDuration() {
        return Duration.ofSeconds(Long.parseLong(getText()));
    }

    @Override
    public com.github.jadamon42.adventure.common.node.WaitNode toStoryNode() {
        if (storyNode == null) {
            storyNode = buildStoryNode();
        }
        return storyNode;
    }

    @Override
    public void clearCachedStoryNode() {
        storyNode = null;
    }

    private com.github.jadamon42.adventure.common.node.WaitNode buildStoryNode() {
        var retval = new com.github.jadamon42.adventure.common.node.WaitNode(getDuration());
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
}
