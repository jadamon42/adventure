package com.github.jadamon42.adventure.builder.element.condition;

import com.github.jadamon42.adventure.common.node.ConditionalText;

public interface ConditionalTextTranslator<T extends ConditionalText> {
    T toConditionalText();
}
