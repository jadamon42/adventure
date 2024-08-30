package com.github.jadamon42.adventure.util;

public interface BooleanFunction<T> extends SerializableFunction<T, Boolean> {
    default BooleanFunction<T> and(BooleanFunction<T> other) {
        return (T t) -> this.apply(t) && other.apply(t);
    }

    default BooleanFunction<T> or(BooleanFunction<T> other) {
        return (T t) -> this.apply(t) || other.apply(t);
    }
}
