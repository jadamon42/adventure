package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.ConnectionType;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;

public class Or extends BasicNode implements ConditionTranslator {
    public Or() {
        NodeHeader header = new NodeHeader("Or", "Or Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.CONDITION);
        footer.addAttachment("Condition 1", ConnectionType.CONDITION);
        footer.addAttachment("Condition 2", ConnectionType.CONDITION);
        setFooter(footer);
    }

    @Override
    public BooleanFunction<Player> getCondition() {
        BooleanFunction<Player> combinedCondition = player -> true;

        for (Node attachment : getAttachmentNodes()) {
            if (attachment instanceof ConditionTranslator conditionNode) {
                BooleanFunction<Player> condition = conditionNode.getCondition();
                combinedCondition = combinedCondition.or(condition);
            }
        }

        return combinedCondition;
    }
}
