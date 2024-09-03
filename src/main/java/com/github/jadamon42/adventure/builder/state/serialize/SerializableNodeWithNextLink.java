package com.github.jadamon42.adventure.builder.state.serialize;

public interface SerializableNodeWithNextLink extends SerializableNode {
    String nextConnectionId();
}
