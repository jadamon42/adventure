package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.serialize.SerializedChoice;
import com.github.jadamon42.adventure.util.BooleanFunction;

public class ConditionalText {
    private final String text;
    private final TextMessage message;
    private final BooleanFunction<Player> condition;

    public ConditionalText(String text) {
        this.text = text;
        this.condition = player -> true;
        this.message = createMessage();
    }

    public ConditionalText(String text, BooleanFunction<Player> condition) {
        this.text = text;
        this.condition = condition;
        this.message = createMessage();
    }

    protected ConditionalText(TextMessage message, BooleanFunction<Player> condition) {
        this.text = message.getText();
        this.condition = condition;
        this.message = message;
    }

    public static ConditionalText fromSerialized(SerializedChoice serialized, GameStateDeserializer data) {
        TextMessage textMessage = data.getMessageMap().get(serialized.messageId());
        return new ConditionalText(textMessage, serialized.condition());
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

    public BooleanFunction<Player> getCondition() {
        return condition;
    }
}
