package com.github.jadamon42.adventure.common.state.serialize;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.jadamon42.adventure.common.node.StoryNode;

import java.util.UUID;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="@type")
public interface SerializableNode {
    UUID id();
    StoryNode accept(SerializableNodeVisitor visitor);
}
