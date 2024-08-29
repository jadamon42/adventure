package com.github.jadamon42.adventure.builder.state;

import java.io.Serializable;

interface SerializableNode extends Serializable {
    String id();

    double layoutX();

    double layoutY();

    String title();

    void accept(SerializableNodeVisitor visitor);
}
