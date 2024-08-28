package com.github.jadamon42.adventure.builder.element.condition;

import com.github.jadamon42.adventure.node.ConditionalText;

public class ConditionalTextInput extends AbstractConditionalTextInput implements ConditionalTextTranslator<ConditionalText> {
    public ConditionalTextInput(String promptText) {
        super(promptText);
    }

    public ConditionalTextInput(String promptText, boolean defaulted) {
        super(promptText, defaulted);
    }

    @Override
    public ConditionalText toConditionalText() {
        return new ConditionalText(getText(), getCondition());
    }
}
