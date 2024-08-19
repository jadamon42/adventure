package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.util.SerializableFunction;

import java.io.Serializable;

public class ConditionalText implements Serializable {
    private final String text;
    private final TextMessage message;
    private final SerializableFunction<Player, Boolean> condition;

    public ConditionalText(String text) {
        this.text = text;
        this.condition = player -> true;
        this.message = createMessage();
    }

    public ConditionalText(String text, SerializableFunction<Player, Boolean> condition) {
        this.text = text;
        this.condition = condition;
        this.message = createMessage();
    }

    protected TextMessage createMessage() {
        return new TextMessage(text, false);
    }

    public boolean isAvailable(Player player) {
        return condition.apply(player);
    }

    public String getText() {
        return text;
    }

    public TextMessage getMessage() {
        return message;
    }
}
