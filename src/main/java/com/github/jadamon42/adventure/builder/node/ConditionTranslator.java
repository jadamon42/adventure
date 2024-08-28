package com.github.jadamon42.adventure.builder.node;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;

public interface ConditionTranslator {
    BooleanFunction<Player> getCondition();
}
