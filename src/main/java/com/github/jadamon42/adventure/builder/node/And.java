package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.ConnectionType;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;

public class And extends BasicNode implements ConditionTranslator {
    private final NodeFooter footer;

    public And() {
        NodeHeader header = new NodeHeader("And", "And Condition");
        setHeader(header);
        footer = new NodeFooter();
        footer.addAttacher(ConnectionType.CONDITION);
        footer.addAttachment("Condition 1", ConnectionType.CONDITION);
        footer.addAttachment("Condition 2", ConnectionType.CONDITION);
        setFooter(footer);
    }

    @Override
    public BooleanFunction<Player> getCondition() {
        BooleanFunction<Player> combinedCondition = player -> true;

        for (Node attachment : footer.getAttachmentNodes()) {
            if (attachment instanceof ConditionTranslator conditionNode) {
                BooleanFunction<Player> condition = conditionNode.getCondition();
                combinedCondition = combinedCondition.and(condition);
            }
        }

        return combinedCondition;
    }
}
