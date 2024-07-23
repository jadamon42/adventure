package com.github.jadamon42.adventure.model;

import com.github.jadamon42.adventure.Message;
import com.github.jadamon42.adventure.node.StoryNode;

import java.io.Serializable;
import java.util.*;

public class GameState implements Serializable {
    private final Player player;
    private final StoryNode currentNode;
    private final List<Message> messageHistory;
    private final Map<UUID, GameState> messageToGameStateMap;

    public GameState(Player player, StoryNode currentNode, List<Message> messageHistory, Map<UUID, GameState> messageToGameStateMap) {
        this.player = new Player(player);
        this.currentNode = currentNode;
        this.messageHistory = new ArrayList<>(messageHistory);
        this.messageToGameStateMap = new HashMap<>(messageToGameStateMap);
    }

    public Player getPlayer() {
        return player;
    }

    public StoryNode getCurrentNode() {
        return currentNode;
    }

    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    public Map<UUID, GameState> getMessageToGameStateMap() {
        return messageToGameStateMap;
    }
}
