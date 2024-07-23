package com.github.jadamon42.adventure;

import com.github.jadamon42.adventure.node.*;

public interface StoryNodeVisitor {
    void visit(ChoiceTextInputNode choiceTextInputNode);

    void visit(LinkableTextNode linkableTextNode);

    void visit(FreeTextInputNode freeTextInputNode);

    void visit(AcquireItemTextNode acquireItemTextNode);

    void visit(AcquireEffectTextNode acquireEffectTextNode);

    void visit(ConditionalTextNode conditionalTextNode);
}
