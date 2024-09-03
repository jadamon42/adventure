package com.github.jadamon42.adventure.builder.state.serialize;

public record SerializableConditional(
        String text,
        String promptText,
        String conditionConnectionId,
        String nextConnectionId,
        boolean isDefault
) {
}
