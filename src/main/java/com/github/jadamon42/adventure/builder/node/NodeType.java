package com.github.jadamon42.adventure.builder.node;

import java.util.function.Supplier;

public enum NodeType {
    ACQUIRE_EFFECT_TEXT_NODE(AcquireEffectTextNode.class.getSimpleName() , AcquireEffectTextNode::new),
    ACQUIRE_ITEM_TEXT_NODE(AcquireItemTextNode.class.getSimpleName(), AcquireItemTextNode::new),
    AND(And.class.getSimpleName(), And::new),
    BRANCH_NODE(BranchNode.class.getSimpleName(), BranchNode::new),
    CHOICE_TEXT_INPUT_NODE(ChoiceTextInputNode.class.getSimpleName(), ChoiceTextInputNode::new),
    EFFECT(Effect.class.getSimpleName(), Effect::new),
    EFFECT_CONDITION(EffectCondition.class.getSimpleName(), EffectCondition::new),
    EXPOSITIONAL_TEXT_NODE(ExpositionalTextNode.class.getSimpleName(), ExpositionalTextNode::new),
    FREE_TEXT_INPUT_NODE(FreeTextInputNode.class.getSimpleName(), FreeTextInputNode::new),
    INPUT_HANDLER(InputHandler.class.getSimpleName(), InputHandler::new),
    ITEM(Item.class.getSimpleName(), Item::new),
    ITEM_CONDITION(ItemCondition.class.getSimpleName(), ItemCondition::new),
    NAME_CONDITION(NameCondition.class.getSimpleName(), NameCondition::new),
    OR(Or.class.getSimpleName(), Or::new),
    SWITCH_NODE(SwitchNode.class.getSimpleName(), SwitchNode::new);

    private final Supplier<Node> supplier;
    private final String name;

    NodeType(String name, Supplier<Node> supplier) {
        this.name = name;
        this.supplier = supplier;
    }

    public Supplier<Node> getSupplier() {
        return supplier;
    }

    public static NodeType fromString(String nodeName) {
        for (NodeType nodeType : NodeType.values()) {
            if (nodeType.name.equals(nodeName)) {
                return nodeType;
            }
        }
        throw new IllegalArgumentException("Unknown node type: " + nodeName);
    }
}
