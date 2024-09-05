package com.github.jadamon42.adventure.common.util;

import java.io.Serializable;
import java.util.function.Function;

/*
 * WARNING
 * `SerializableFunction` gets serialized to a string during JSON serialization.
 * Changes to instances of this interface will break compatibility with existing save files.
 */
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {
}
