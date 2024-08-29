package com.github.jadamon42.adventure.builder.state;

import java.io.Serializable;

record SerializableOption(
        String text,
        String promptText,
        String conditionConnectionId,
        String nextConnectionId,
        boolean isDefault
) implements Serializable {
}
