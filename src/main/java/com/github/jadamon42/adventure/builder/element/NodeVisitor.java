package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.builder.node.*;

public interface NodeVisitor {
    void visit(AcquireEffectTextNode acquireEffectTextNode);

    void visit(AcquireItemTextNode acquireItemTextNode);

    void visit(And and);

    void visit(BranchNode branchNode);

    void visit(ChoiceTextInputNode choiceTextInputNode);

    void visit(Effect effect);

    void visit(EffectCondition effectCondition);

    void visit(ExpositionalTextNode expositionalTextNode);

    void visit(FreeTextInputNode freeTextInputNode);

    void visit(InputHandler inputHandler);

    void visit(Item item);

    void visit(ItemCondition itemCondition);

    void visit(NameCondition nameCondition);

    void visit(Or or);

    void visit(Start start);

    void visit(SwitchNode switchNode);
}
