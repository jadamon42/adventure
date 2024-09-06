package com.github.jadamon42.adventure.runner.ui.element;

public class MessageAction {
    private final MessageContainer messageContainer;
    private final WaitAmount waitMessage;

    public MessageAction(MessageContainer messageContainer, WaitAmount waitMessage) {
        this.messageContainer = messageContainer;
        this.waitMessage = waitMessage;
    }

    public static MessageAction of(MessageContainer messageContainer) {
        return new MessageAction(messageContainer, null);
    }

    public static MessageAction of(WaitAmount waitMessage) {
        return new MessageAction(null, waitMessage);
    }

    public MessageContainer getMessageContainer() {
        return messageContainer;
    }

    public WaitAmount getWaitMessage() {
        return waitMessage;
    }
}
