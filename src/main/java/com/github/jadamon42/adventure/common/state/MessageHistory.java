package com.github.jadamon42.adventure.common.state;

import com.github.jadamon42.adventure.common.model.TextMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageHistory implements Iterable<TextMessage> {
    private final List<TextMessage> textMessages;

    public MessageHistory() {
        this.textMessages = new ArrayList<>();
    }

    public MessageHistory(MessageHistory messageHistory) {
        this.textMessages = new ArrayList<>(messageHistory.textMessages);
    }

    public void add(TextMessage textMessage) {
        textMessages.add(textMessage);
    }

    public void addAll(List<TextMessage> textMessages) {
        this.textMessages.addAll(textMessages);
    }

    @Override
    public Iterator<TextMessage> iterator() {
        return textMessages.iterator();
    }
}
