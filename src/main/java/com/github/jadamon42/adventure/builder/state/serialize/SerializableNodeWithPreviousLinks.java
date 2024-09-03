package com.github.jadamon42.adventure.builder.state.serialize;

import java.util.List;

public interface SerializableNodeWithPreviousLinks extends SerializableNode {
    List<String> previousConnectionIds();
}
