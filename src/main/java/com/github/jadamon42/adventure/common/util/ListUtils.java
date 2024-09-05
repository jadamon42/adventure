package com.github.jadamon42.adventure.common.util;

import java.util.List;

public class ListUtils {
    public static <T> T getFirst(List<T> list) {
        return list.isEmpty() ? null : list.getFirst();
    }

    public static <T> T getLast(List<T> list) {
        return list.isEmpty() ? null : list.getLast();
    }
}
