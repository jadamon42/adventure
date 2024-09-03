package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.util.BooleanFunction;

import java.util.List;

public class And extends BasicNode implements ConditionTranslator, VisitableNode {
    private final AttachmentLink condition1Link;
    private final AttachmentLink condition2Link;
    private final AttachmentLink conditionLink;

    public And() {
        NodeHeader header = new NodeHeader("And", "And Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        conditionLink = footer.addAttacher(ConnectionType.CONDITION);
        condition1Link = footer.addAttachment("Condition 1", ConnectionType.CONDITION);
        condition2Link = footer.addAttachment("Condition 2", ConnectionType.CONDITION);
        setFooter(footer);
    }

    @Override
    public BooleanFunction<Player> getCondition() {
        BooleanFunction<Player> combinedCondition = player -> true;

        for (Node attachment : getAttachmentNodes()) {
            if (attachment instanceof ConditionTranslator conditionNode) {
                BooleanFunction<Player> condition = conditionNode.getCondition();
                combinedCondition = combinedCondition.and(condition);
            }
        }

        return combinedCondition;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public String getCondition1ConnectionId() {
        return getFirst(getFooter().getAttachmentConnectionIds());
    }

    public String getCondition2ConnectionId() {
        return getLast(getFooter().getAttachmentConnectionIds());
    }

    public List<String> getConditionConnectionIds() {
        return getFooter().getAttacherConnectionIds();
    }

    public void setCondition1Connection(ConnectionLine connectionLine) {
        condition1Link.addConnection(connectionLine);
    }

    public void setCondition2Connection(ConnectionLine connectionLine) {
        condition2Link.addConnection(connectionLine);
    }

    public void addConditionConnection(ConnectionLine connectionLine) {
        conditionLink.addConnection(connectionLine);
    }
}
