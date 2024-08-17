package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.SerializableFunction;

import java.io.Serializable;
import java.util.function.Function;

public class TextChoice implements Serializable {
    private final String text;
    private final SerializableFunction<Player, Boolean> condition;

    public TextChoice(String text) {
        this.text = text;
        this.condition = player -> true;
    }

    public TextChoice(String text, SerializableFunction<Player, Boolean> condition) {
        this.text = text;
        this.condition = condition;
    }

    public boolean isAvailable(Player player) {
        return condition.apply(player);
    }

    public String getText() {
        return text;
    }
}
