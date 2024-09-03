package com.github.jadamon42.adventure.builder.state;

record SerializableOption(
        String text,
        String promptText,
        String conditionConnectionId,
        String nextConnectionId,
        boolean isDefault
) {
}
