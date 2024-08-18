package com.github.jadamon42.adventure.engine.console;

import com.github.jadamon42.adventure.engine.GameEngine;
import com.github.jadamon42.adventure.model.*;
import com.github.jadamon42.adventure.node.*;
import com.github.jadamon42.adventure.util.SerializableBiFunction;
import com.github.jadamon42.adventure.util.TextInterpolator;

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
        LinkedTextChoice availableChoice = node.getChoice(player);
        handleOutput(availableChoice.getText());
        currentNode = availableChoice.getNextNode();
    }

    @Override
    public void visit(SwitchNode node) {
        TextChoice availableChoice = node.getChoice(player);
        handleOutput(availableChoice.getText());
        currentNode = node.getNextNode();
    }

    @Override
    public void visit(ChoiceTextInputNode node) {
        handleInteractableOutputNode(node);

        List<LinkedTextChoice> availableChoices = node.getChoices(player);
        int choiceIndex = inputHandler.getMultipleChoiceInput(availableChoices);
        LinkedTextChoice choice = availableChoices.get(choiceIndex);
        String text = TextInterpolator.interpolate(choice.getText(), player);
        handleInput(text);
        currentNode = choice.getNextNode();
    }

    @Override
    public void visit(FreeTextInputNode node) {
        handleInteractableOutputNode(node);

        String input = inputHandler.getFreeTextInput();
        SerializableBiFunction<Player, Object, PlayerDelta> textConsumer = node.getTextConsumer();
        if (textConsumer != null) {
            textConsumer.apply(player, input);
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
    public void visit(LinkableStoryTextNode node) {
        handleOutput(node.getText());
        currentNode = node.getNextNode();
    }

    private void handleInteractableOutputNode(StoryTextNode node) {
        String text = TextInterpolator.interpolate(node.getText(), player);
        System.out.println(text);
        Message message = new Message(text, false);
    }

    private void handleOutput(String output) {
        String text = TextInterpolator.interpolate(output, player);
        System.out.println(text);
        Message message = new Message(text, false);
    }

    private void handleInput(String input) {
        Message message = new Message(input, true);
    }
}
