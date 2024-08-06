package com.github.jadamon42.adventure;

import com.github.jadamon42.adventure.node.*;

public interface StoryNodeVisitor {
    void visit(LinkableTextNode node);

    void visit(ChoiceTextInputNode node);

    void visit(FreeTextInputNode node);

    void visit(AcquireItemTextNode node);

    void visit(AcquireEffectTextNode node);

    void visit(BranchTextNode node);

    void visit(ConditionalTextNode node);
}
