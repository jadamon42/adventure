package com.github.jadamon42.adventure.model;

import com.github.jadamon42.adventure.node.*;
import com.github.jadamon42.adventure.ui.JavaFXGameEngine;
import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiAdventureGameTest extends Application {

    private final static Effect superStrength = new Effect("super strength");
    private final static Item orb = new Item("orb");

    public static void main(String[] args) {
        launch(args);
    }

    private VBox messagePanel;
    private TextField inputField;
    private ScrollPane scrollPane;

    @Override
    public void start(Stage stage) {
        Player player = new Player();
        JavaFXGameEngine gameEngine = new JavaFXGameEngine(player, start(player));
        gameEngine.initialize(stage);
        gameEngine.startGame();
    }

    private static LinkableTextNode start(Player player) {
        LinkableTextNode node = new ExpositionalTextNode("** First Adventure: The Room **");
        node.then(new FreeTextInputNode("What is your name?", player::setName))
            .then(playerNameAndExposition(player));
        return node;
    }

    private static LinkableTextNode playerNameAndExposition(Player player) {
        LinkableTextNode node = new ConditionalTextNode(
                new TextChoice("$PLAYER_NAME? What a dumb name. Anyway, you are in a room (with a dumb name).", p -> p.getName().equalsIgnoreCase("nick") || p.getName().equalsIgnoreCase("victor") || p.getName().equalsIgnoreCase("nicholas") || p.getName().equalsIgnoreCase("neck")),
                new TextChoice("$PLAYER_NAME? What a cool name! Anyway, you are in a room (and everyone is probably jealous of how cool you are).", p -> p.getName().equalsIgnoreCase("jonathan") || p.getName().equalsIgnoreCase("jon")),
                new TextChoice("$PLAYER_NAME? You sound old. Anyway, you're in a room (and you're old).", p -> p.getName().equalsIgnoreCase("dave") || p.getName().equalsIgnoreCase("david") || p.getName().equalsIgnoreCase("natalie")),
                new TextChoice("Hello, $PLAYER_NAME. You are in a room.")
        );
        node.then(new ExpositionalTextNode("You see a door."))
            .then(new ChoiceTextInputNode("Enter it?",
                                          new LinkableTextChoice("Yes", openTheDoor()),
                                          new LinkableTextChoice("No", boringEnd()),
                                          new LinkableTextChoice("Yes, but with style", openTheDoor(), p -> p.getName().contains("cool"))));
        return node;
    }

    private static LinkableTextNode boringEnd() {
        return new ExpositionalTextNode("Your life is boring.");
    }

    private static LinkableTextNode openTheDoor() {
        LinkableTextNode node = new ExpositionalTextNode("You open the door and enter a new room.");
        node.then(new ChoiceTextInputNode("In the new new room, you see a glowing orb. What do you do?",
                                          new LinkableTextChoice("Pick it up and put it in my backpack", pickUpTheOrb()),
                                          new LinkableTextChoice("Destroy it", destroyTheOrb()),
                                          new LinkableTextChoice("Leave it", boringEnd())));
        return node;
    }

    private static LinkableTextNode pickUpTheOrb() {
        LinkableTextNode node = new AcquireItemTextNode("You pick up the orb.", orb);
        node.then(leaveTheRoomChoice());
        return node;
    }

    private static LinkableTextNode destroyTheOrb() {
        LinkableTextNode node = new AcquireEffectTextNode("Destroying the orb released it's power, you feel hale and hearty.", superStrength);
        node.then(leaveTheRoomChoice());
        return node;
    }

    private static LinkableTextNode leaveTheRoomChoice() {
        return new ChoiceTextInputNode("Would you like to leave the room, or stay for a while?",
                                       new LinkableTextChoice("Leave the room", leaveTheRoomEnding()),
                                       new LinkableTextChoice("Stay for a while", stayForAWhile()));
    }

    private static LinkableTextNode leaveTheRoomEnding() {
        return new BranchTextNode(
                new LinkableTextChoice("You leave the room with a useless orb. Maybe some day you can sell it.", p -> p.hasItem(orb)),
                new LinkableTextChoice("You leave the room.")
        );
    }

    private static LinkableTextNode stayForAWhile() {
        LinkableTextNode node = new ExpositionalTextNode("You stay for a while.");
        node.then(new ExpositionalTextNode("After 14 days of staying in this room, you feel dehydrated, hungry, tired, and weak. You ask yourself why you stayed in this room for so long, it was a very stupid decision."))
            .then(new ExpositionalTextNode("Suddenly, an ogre enters the room."))
            .then(new ExpositionalTextNode("\"Hey! What are you doing in my room?\" shouts the Ogre. \"I'm going to eat you!\""))
            .then(
                    new ChoiceTextInputNode("What do you do?",
                                            new LinkableTextChoice("Throw the orb at the ogre", throwTheOrb(), p -> p.hasItem(orb)),
                                            new LinkableTextChoice("Stand up, raise my fists, and fight the ogre", fightTheOgre()),
                                            new LinkableTextChoice("Run away", leaveTheRoomEnding()),
                                            new LinkableTextChoice("Apologize and explain that I was just exploring", deathByOgre())
                    )
            );
        return node;
    }

    private static LinkableTextNode throwTheOrb() {
        LinkableTextNode node = new ExpositionalTextNode("You throw the orb at the ogre. In shatters upon impact, and a power is released. The ogre absorbs the power, and you feel very frightened.");
        node.then(new ExpositionalTextNode("The ogre smiles, and takes one step closer to you. You poop your pants in fear."))
            .then(new ExpositionalTextNode("The ogre extends a finger to touch you, and as he does, every bone in your body breaks. You scream in agony."))
            .then(new ExpositionalTextNode("The ogre laughs as you lay on the ground, unable to move. He picks you up and eats you. You die."));
        return node;
    }

    private static LinkableTextNode fightTheOgre() {
        LinkableTextNode node = new ExpositionalTextNode("The ogre laughs as you hobble over to him. You have very little energy because you are so exhausted.");
        node.then(
                new BranchTextNode(
                        new LinkableTextChoice("You throw the first punch, and the ogre is thrown back as if he was hit by a train. You are amazed at your newfound strength. It must be the orb...", null, p -> p.hasEffect(superStrength)),
                        new LinkableTextChoice("You throw the first punch.", deathByOgre())
                )
        );
        return node;
    }

    private static LinkableTextNode deathByOgre() {
        return new ExpositionalTextNode("The ogre doesn't care. He eats you, and you die.");
    }
}
