package com.github.jadamon42.adventure.builder.state.serialize;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="@type")
public interface SerializableNode {
    String id();

    double layoutX();

    double layoutY();

    String title();

    void accept(SerializableNodeVisitor visitor);
}
