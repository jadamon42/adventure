package com.github.jadamon42.adventure.builder.node;

import java.util.function.Supplier;

public enum NodeType {
    ACQUIRE_EFFECT_TEXT_NODE(AcquireEffectTextNode.class.getSimpleName() , AcquireEffectTextNode::new, "Acquire Effect"),
    ACQUIRE_ITEM_TEXT_NODE(AcquireItemTextNode.class.getSimpleName(), AcquireItemTextNode::new, "Acquire Item"),
    AND(And.class.getSimpleName(), And::new, "And"),
    BRANCH_NODE(BranchNode.class.getSimpleName(), BranchNode::new, "Branch"),
    CHOICE_TEXT_INPUT_NODE(ChoiceTextInputNode.class.getSimpleName(), ChoiceTextInputNode::new, "Multiple Choice"),
    EFFECT(Effect.class.getSimpleName(), Effect::new, "Effect"),
    EFFECT_CONDITION(EffectCondition.class.getSimpleName(), EffectCondition::new, "Has Effect"),
    EXPOSITIONAL_TEXT_NODE(ExpositionalTextNode.class.getSimpleName(), ExpositionalTextNode::new, "Text"),
    FREE_TEXT_INPUT_NODE(FreeTextInputNode.class.getSimpleName(), FreeTextInputNode::new, "Free Text"),
    INPUT_HANDLER(InputHandler.class.getSimpleName(), InputHandler::new, "Input Handler"),
    ITEM(Item.class.getSimpleName(), Item::new, "Item"),
    ITEM_CONDITION(ItemCondition.class.getSimpleName(), ItemCondition::new, "Has Item"),
    NAME_CONDITION(NameCondition.class.getSimpleName(), NameCondition::new, "Has Name"),
    OR(Or.class.getSimpleName(), Or::new, "Or"),
    SWITCH_NODE(SwitchNode.class.getSimpleName(), SwitchNode::new, "Switch"),
    WAIT_NODE(WaitNode.class.getSimpleName(), WaitNode::new, "Wait");

    private final Supplier<Node> supplier;
    private final String name;
    private final String displayName;

    NodeType(String name, Supplier<Node> supplier, String displayName) {
        this.name = name;
        this.supplier = supplier;
        this.displayName = displayName;
    }

    public Supplier<Node> getSupplier() {
        return supplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
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
