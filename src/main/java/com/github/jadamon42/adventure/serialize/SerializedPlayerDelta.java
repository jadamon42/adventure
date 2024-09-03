package com.github.jadamon42.adventure.serialize;

import java.util.List;
import java.util.UUID;

public record SerializedPlayerDelta(
    String name,
    List<UUID> itemIds,
    List<UUID> effectIds
) {
}
