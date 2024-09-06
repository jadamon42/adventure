package com.github.jadamon42.adventure.common.node;

public interface StoryNodeVisitor {
    void visit(ExpositionalTextNode node);

    void visit(ChoiceTextInputNode node);

    void visit(FreeTextInputNode node);

    void visit(AcquireItemTextNode node);

    void visit(AcquireEffectTextNode node);

    void visit(BranchNode node);

    void visit(SwitchNode node);

    void visit(WaitNode waitNode);
}
