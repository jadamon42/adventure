package com.github.jadamon42.adventure.builder.state;

import java.util.List;

interface SerializableNodeWithPreviousLinks extends SerializableNode {
    List<String> previousConnectionIds();
}
