package com.github.jadamon42.adventure.common.state.serialize;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record SerializablePlayerDelta(
    String name,
    List<Map.Entry<String, String>> customAttributes,
    List<UUID> itemIds,
    List<UUID> effectIds
) {
}
