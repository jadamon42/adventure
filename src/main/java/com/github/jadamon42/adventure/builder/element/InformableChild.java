package com.github.jadamon42.adventure.builder.element;

public interface InformableChild {
    default void onParentDragged() {}

    default void onParentDeleted() {}
}
