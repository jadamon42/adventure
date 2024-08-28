package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.builder.element.ConditionTranslator;
import com.github.jadamon42.adventure.builder.element.NodeFooter;
import com.github.jadamon42.adventure.builder.element.NodeHeader;
import com.github.jadamon42.adventure.builder.element.connection.ConnectionType;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;

public class EffectCondition extends BasicNode implements ConditionTranslator {
    public EffectCondition() {
        NodeHeader header = new NodeHeader("Has Effect", "Effect Condition");
        setHeader(header);
        NodeFooter footer = new NodeFooter();
        footer.addAttacher(ConnectionType.CONDITION);
        footer.addAttachment("Attach Effect", ConnectionType.EFFECT);
        setFooter(footer);
    }

    @Override
    public BooleanFunction<Player> getCondition() {
        BooleanFunction<Player> hasEffect = player -> false;
        com.github.jadamon42.adventure.model.Effect effect = null;
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
}
