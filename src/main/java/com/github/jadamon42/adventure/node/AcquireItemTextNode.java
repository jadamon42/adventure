package com.github.jadamon42.adventure.node;

import com.github.jadamon42.adventure.model.Item;
import com.github.jadamon42.adventure.model.TextMessage;
import com.github.jadamon42.adventure.serialize.GameStateDeserializer;
import com.github.jadamon42.adventure.serialize.SerializedAcquireItemTextNode;


public class AcquireItemTextNode extends LinkableStoryTextNode {
    private final Item item;
    private final TextMessage message;

    public AcquireItemTextNode(String text, Item item) {
        super(text);
        this.item = item;
        this.message = new TextMessage(this.getText(), false);
    }

    private AcquireItemTextNode(TextMessage message, Item item) {
        super(message.getText());
        this.item = item;
        this.message = message;
    }

    public static AcquireItemTextNode fromSerialized(SerializedAcquireItemTextNode serialized, GameStateDeserializer data) {
        TextMessage textMessage = data.getMessageMap().get(serialized.messageId());
        Item item = data.getItemMap().get(serialized.itemId());
        return new AcquireItemTextNode(textMessage, item);
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
