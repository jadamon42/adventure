package com.github.jadamon42.adventure.util;

import java.io.Serializable;
import java.util.function.Function;

public interface BooleanFunction<T> extends Function<T, Boolean>, Serializable {
    default BooleanFunction<T> and(BooleanFunction<T> other) {
        return (T t) -> this.apply(t) && other.apply(t);
    }

    default BooleanFunction<T> or(BooleanFunction<T> other) {
        return (T t) -> this.apply(t) || other.apply(t);
    }
}
