package com.github.jadamon42.adventure.builder.element;

import java.util.List;

public interface InformableChild {
    default void onParentDragged() {}

    default void onParentDeleted() {}

    default <T> T getFirst(List<T> list) {
        return list.isEmpty() ? null : list.getFirst();
    }
}
