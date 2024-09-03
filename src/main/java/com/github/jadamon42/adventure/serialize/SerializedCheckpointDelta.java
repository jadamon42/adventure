package com.github.jadamon42.adventure.serialize;

import java.util.List;
import java.util.UUID;

public record SerializedCheckpointDelta(
    List<UUID> messageIds,
    SerializedPlayerDelta playerDelta,
    UUID currentNodeId,
    UUID currentMessageId
){}
