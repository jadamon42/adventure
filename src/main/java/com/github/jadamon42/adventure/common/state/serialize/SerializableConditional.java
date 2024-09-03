package com.github.jadamon42.adventure.common.state.serialize;

import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.util.BooleanFunction;

import java.util.UUID;

public record SerializableConditional(
        UUID messageId,
        UUID nextNodeId,
        BooleanFunction<Player> condition
) {
}
