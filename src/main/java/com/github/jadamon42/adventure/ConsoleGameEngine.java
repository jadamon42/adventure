package com.github.jadamon42.adventure;

import com.github.jadamon42.adventure.model.*;
import com.github.jadamon42.adventure.node.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class ConsoleGameEngine implements GameEngine, StoryNodeVisitor {
    private Player player;
    private StoryNode currentNode;
    private List<Message> messageHistory;
    private Map<UUID, GameState> interactableCheckpoints;
    private final InputHandler inputHandler;
    private final GameStateManager gameStateManager;

    public ConsoleGameEngine(Player player, StoryNode startNode, InputHandler inputHandler) {
        this.player = player;
        this.currentNode = startNode;
        this.messageHistory = new ArrayList<>();
        this.interactableCheckpoints = new HashMap<>();
        this.inputHandler = inputHandler;
        this.gameStateManager = new GameStateManager();
    }

    @Override
    public void start() {
        while (currentNode != null) {
            currentNode.accept(this);
        }
    }

    @Override
    public void loadGame(String saveFile) {
        try {
            GameState gameState = gameStateManager.loadGame(saveFile);
            this.player = gameState.getPlayer();
            this.currentNode = gameState.getCurrentNode();
            this.messageHistory = gameState.getMessageHistory();
            this.interactableCheckpoints = gameState.getMessageToGameStateMap();
            replayMessages();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load game: " + e.getMessage());
        }
    }

    @Override
    public void saveGame(String saveFile) {
        try {
            GameState gameState = new GameState(player, currentNode, messageHistory, interactableCheckpoints);
            gameStateManager.saveGame(saveFile, gameState);
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public void resetToMessage(UUID messageId) {
        GameState savedState = interactableCheckpoints.get(messageId);
        if (savedState != null) {
            this.player = new Player(savedState.getPlayer());
            this.currentNode = savedState.getCurrentNode();
            this.messageHistory = savedState.getMessageHistory();
            this.interactableCheckpoints = savedState.getMessageToGameStateMap();
            replayMessages();
        } else {
            System.out.println("Invalid message ID");
        }
    }

    @Override
    public void visit(ConditionalTextNode node) {
        TextChoice availableChoice = node.getChoice(player);
        handleOutput(availableChoice.getText());
        currentNode = availableChoice.getNextNode();
    }

    @Override
    public void visit(ChoiceTextInputNode node) {
        handleInteractableOutputNode(node);

        List<TextChoice> availableChoices = node.getChoices(player);
        int choiceIndex = inputHandler.getMultipleChoiceInput(availableChoices);
        TextChoice choice = availableChoices.get(choiceIndex);
        String text = TextInterpolator.interpolate(choice.getText(), player);
        handleInput(text);
        currentNode = choice.getNextNode();
    }

    @Override
    public void visit(FreeTextInputNode node) {
        handleInteractableOutputNode(node);

        String input = inputHandler.getFreeTextInput();
        Consumer<String> textConsumer = node.getTextConsumer();
        if (textConsumer != null) {
            textConsumer.accept(input);
        }
        handleInput(input);
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireEffectTextNode node) {
        handleOutput(node.getText());
        player.addEffect(node.getEffect());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireItemTextNode node) {
        handleOutput(node.getText());
        player.addItem(node.getItem());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(LinkableTextNode node) {
        handleOutput(node.getText());
        currentNode = node.getNextNode();
    }

    private void handleInteractableOutputNode(LinkableTextNode node) {
        String text = TextInterpolator.interpolate(node.getText(), player);
        System.out.println(text);
        Message message = new Message(text, false);
        messageHistory.add(message);
        interactableCheckpoints.put(message.getId(), new GameState(new Player(player), node, new ArrayList<>(messageHistory), new HashMap<>(interactableCheckpoints)));
    }

    private void handleOutput(String output) {
        String text = TextInterpolator.interpolate(output, player);
        System.out.println(text);
        Message message = new Message(text, false);
        messageHistory.add(message);
    }

    private void handleInput(String input) {
        Message message = new Message(input, true);
        messageHistory.add(message);
    }

    private void replayMessages() {
        for (Message message : messageHistory) {
            if (message.isPlayerMessage()) {
                System.out.println("Player: " + message.getText());
            } else {
                System.out.println("Game: " + message.getText());
            }
        }
    }
}
