package com.github.jadamon42.adventure.builder.state;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="@type")
interface SerializableNode extends Serializable {
    String id();

    double layoutX();

    double layoutY();

    String title();

    void accept(SerializableNodeVisitor visitor);
}
