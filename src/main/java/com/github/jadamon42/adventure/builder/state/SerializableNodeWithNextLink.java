package com.github.jadamon42.adventure.builder.state;

interface SerializableNodeWithNextLink extends SerializableNode {
    String nextConnectionId();
}
