package com.github.jadamon42.adventure.builder.element.condition;

import com.github.jadamon42.adventure.node.ConditionalText;

public class DefaultedConditionalTextInput extends ConditionalTextInput {
    public DefaultedConditionalTextInput(String promptText) {
        super(promptText, true);
    }

    @Override
    public ConditionalText toConditionalText() {
        return new ConditionalText(getText());
    }
}
