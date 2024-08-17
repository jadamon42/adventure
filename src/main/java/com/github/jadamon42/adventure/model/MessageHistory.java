package com.github.jadamon42.adventure.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MessageHistory implements Iterable<Message>, Serializable {
    private final List<Message> messages;

    public MessageHistory() {
        this.messages = new ArrayList<>();
    }

    public MessageHistory(MessageHistory messageHistory) {
        this.messages = new ArrayList<>(messageHistory.messages);
    }

    private MessageHistory(List<Message> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public void add(Message message) {
        messages.add(message);
    }

    public void addAll(Message[] messages) {
        this.messages.addAll(Arrays.asList(messages));
    }

    @Override
    public Iterator<Message> iterator() {
        return new MessageHistoryIterator();
    }

    private class MessageHistoryIterator implements Iterator<Message> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < messages.size();
        }

        @Override
        public Message next() {
            return messages.get(currentIndex++);
        }
    }
}
