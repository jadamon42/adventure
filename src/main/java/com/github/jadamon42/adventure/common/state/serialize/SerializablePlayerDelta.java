package com.github.jadamon42.adventure.common.state.serialize;

import com.github.jadamon42.adventure.common.model.CustomAttribute;

import java.util.List;
import java.util.UUID;

public record SerializablePlayerDelta(
    String name,
    List<CustomAttribute> customAttributes,
    List<UUID> itemIds,
    List<UUID> effectIds
) {
}
