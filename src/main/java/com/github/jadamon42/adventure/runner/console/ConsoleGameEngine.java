package com.github.jadamon42.adventure.runner.console;

import com.github.jadamon42.adventure.common.node.*;
import com.github.jadamon42.adventure.runner.GameEngine;
import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.util.PlayerDeltaBiFunction;

import java.util.*;

public class ConsoleGameEngine implements GameEngine, StoryNodeVisitor {
    private final Player player;
    private StoryNode currentNode;
    private final InputHandler inputHandler;

    public ConsoleGameEngine(Player player, StoryNode startNode) {
        this.player = player;
        this.currentNode = startNode;
        this.inputHandler = new ConsoleInputHandler();
    }

    @Override
    public void startGame() {
        while (currentNode != null) {
            currentNode.accept(this);
        }
    }

    @Override
    public void loadGame(String saveFile) {
    }

    @Override
    public void saveGame(String saveFile) {
    }

    @Override
    public Player getPlayer() {
        return player;
    }

//    public void resetToMessage(UUID messageId) {
//        GameState savedState = interactableCheckpoints.get(messageId);
//        if (savedState != null) {
//            this.player = new Player(savedState.getPlayer());
//            this.currentNode = savedState.getCurrentNode();
//            this.messageHistory = savedState.getMessageHistory();
//            this.interactableCheckpoints = savedState.getMessageToGameStateMap();
//            replayMessages();
//        } else {
//            System.out.println("Invalid message ID");
//        }
//    }

    @Override
    public void visit(BranchNode node) {
        LinkedConditionalText availableChoice = node.getBranch(player);
        handleOutput(availableChoice.getMessage().getInterpolatedText(player));
        currentNode = availableChoice.getNextNode();
    }

    @Override
    public void visit(SwitchNode node) {
        ConditionalText availableChoice = node.getCase(player);
        handleOutput(availableChoice.getMessage().getInterpolatedText(player));
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(ChoiceTextInputNode node) {
        handleInteractableOutputNode(node);

        List<LinkedTextChoice> availableChoices = node.getChoices(player);
        int choiceIndex = inputHandler.getMultipleChoiceInput(availableChoices);
        LinkedConditionalText choice = availableChoices.get(choiceIndex);
        currentNode = choice.getNextNode();
    }

    @Override
    public void visit(FreeTextInputNode node) {
        handleInteractableOutputNode(node);

        String input = inputHandler.getFreeTextInput();
        PlayerDeltaBiFunction<Object> textConsumer = node.getTextConsumer();
        if (textConsumer != null) {
            textConsumer.apply(player, input);
        }
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireEffectTextNode node) {
        handleOutput(node.getMessage().getInterpolatedText(player));
        player.addEffect(node.getEffect());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(AcquireItemTextNode node) {
        handleOutput(node.getMessage().getInterpolatedText(player));
        player.addItem(node.getItem());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(ExpositionalTextNode node) {
        handleOutput(node.getMessage().getInterpolatedText(player));
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(WaitNode node) {
        try {
            Thread.sleep(node.getDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentNode = node.getNextNode();
    }

    private void handleInteractableOutputNode(StoryTextNode node) {
        String text = node.getMessage().getInterpolatedText(player);
        System.out.println(text);
    }

    private void handleOutput(String text) {
        System.out.println(text);
    }
}
