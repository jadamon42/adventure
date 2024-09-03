package com.github.jadamon42.adventure.serialize;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.jadamon42.adventure.node.StoryNode;

import java.util.UUID;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="@type")
public interface SerializedNode {
    UUID id();
    StoryNode accept(SerializedNodeVisitor visitor);
}
