package com.github.jadamon42.adventure.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MessageHistory implements Iterable<TextMessage>, Serializable {
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

    public void addAll(TextMessage[] textMessages) {
        this.textMessages.addAll(Arrays.asList(textMessages));
    }

    @Override
    public Iterator<TextMessage> iterator() {
        return new MessageHistoryIterator();
    }

    private class MessageHistoryIterator implements Iterator<TextMessage> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < textMessages.size();
        }

        @Override
        public TextMessage next() {
            return textMessages.get(currentIndex++);
        }
    }
}
