package com.github.jadamon42.adventure.util;

import com.github.jadamon42.adventure.model.PlayerDelta;

import java.io.Serializable;
import java.util.function.BiFunction;

public interface PlayerDeltaBiFunction<T, U> extends BiFunction<T, U, PlayerDelta>, Serializable {
}
