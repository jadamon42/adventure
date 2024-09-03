package com.github.jadamon42.adventure.serialize;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.BooleanFunction;

import java.util.UUID;

public record SerializedChoice(
        UUID messageId,
        UUID nextNodeId,
        BooleanFunction<Player> condition
) {
}
