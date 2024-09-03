package com.github.jadamon42.adventure.builder.element;

import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.util.BooleanFunction;

public interface ConditionTranslator {
    BooleanFunction<Player> getCondition();
}
