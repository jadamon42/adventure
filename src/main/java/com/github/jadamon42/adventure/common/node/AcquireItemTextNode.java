package com.github.jadamon42.adventure.common.node;

import com.github.jadamon42.adventure.common.model.Item;
import com.github.jadamon42.adventure.common.model.TextMessage;
import com.github.jadamon42.adventure.common.state.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.common.state.serialize.SerializableAcquireItemTextNode;

import java.util.UUID;


public class AcquireItemTextNode extends LinkableStoryTextNode {
    private final Item item;
    private final TextMessage message;

    public AcquireItemTextNode(String text, Item item) {
        super(text);
        this.item = item;
        this.message = new TextMessage(this.getText(), false);
    }

    private AcquireItemTextNode(UUID id, TextMessage message, Item item) {
        super(id, message.getText());
        this.item = item;
        this.message = message;
    }

    public static AcquireItemTextNode fromSerialized(SerializableAcquireItemTextNode serialized, GameStateDeserializer data) {
        TextMessage textMessage = data.getMessageMap().get(serialized.messageId());
        Item item = data.getItemMap().get(serialized.itemId());
        return new AcquireItemTextNode(serialized.id(), textMessage, item);
    }

    public Item getItem() {
        return item;
    }

    @Override
    public TextMessage getMessage() {
        return message;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}
