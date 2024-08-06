package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Player;

import java.util.function.Function;

public class TextChoice {
    private final String text;
    private final Function<Player, Boolean> condition;

    public TextChoice(String text) {
        this.text = text;
        this.condition = player -> true;
    }

    public TextChoice(String text, Function<Player, Boolean> condition) {
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
