package com.github.jadamon42.adventure.common.state.serialize;

import java.util.List;
import java.util.UUID;

public record SerializableCheckpointDelta(
    List<UUID> messageIds,
    SerializablePlayerDelta playerDelta,
    UUID currentNodeId,
    UUID currentMessageId
){}
