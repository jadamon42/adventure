package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.*;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.util.BooleanFunction;
import com.github.jadamon42.adventure.common.util.ListHelper;

import java.util.List;

public class EffectCondition extends BasicNode implements ConditionTranslator, VisitableNode {
    private final AttachmentLink effectLink;
    private final AttachmentLink conditionLink;

    public EffectCondition() {
        NodeHeader header = new NodeHeader("Has Effect", "Effect Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        conditionLink = footer.addAttacher(ConnectionType.CONDITION);
        effectLink = footer.addAttachment("Attach Effect", ConnectionType.EFFECT);
        setFooter(footer);
    }

    @Override
    public BooleanFunction<Player> getCondition() {
        BooleanFunction<Player> hasEffect = player -> false;
        com.github.jadamon42.adventure.common.model.Effect effect = null;
        for (Node node : getAttachmentNodes()) {
            if (node instanceof Effect effectNode) {
                effect = effectNode.getEffectModel();
            }
        }
        if (effect != null) {
            String effectName = effect.getName();
            hasEffect = player -> player.hasEffect(effectName);
        }
        return hasEffect;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public String getEffectConnectionId() {
        return ListHelper.getFirst(getFooter().getAttachmentConnectionIds());
    }

    public List<String> getConditionConnectionIds() {
        return getFooter().getAttacherConnectionIds();
    }

    public void setEffectConnection(ConnectionLine connectionLine) {
        effectLink.addConnection(connectionLine);
    }

    public void addConditionConnection(ConnectionLine connectionLine) {
        conditionLink.addConnection(connectionLine);
    }
}
