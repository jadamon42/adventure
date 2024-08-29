package com.github.jadamon42.adventure.builder.element;

import java.util.List;

public interface DraggableChild {
    default void onParentDragged() {}

    default <T> T getFirst(List<T> list) {
        return list.isEmpty() ? null : list.getFirst();
    }
}
