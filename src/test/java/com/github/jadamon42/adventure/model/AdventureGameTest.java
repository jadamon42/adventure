package com.github.jadamon42.adventure.model;

import com.github.jadamon42.adventure.engine.console.ConsoleGameEngine;
import com.github.jadamon42.adventure.node.*;

class AdventureGameTest {

    private final static Effect superStrength = new Effect("super strength");
    private final static Item orb = new Item("orb");

    public static void main(String[] args) {
        Player player = new Player();
        ConsoleGameEngine adventureGame = new ConsoleGameEngine(player, start(player));
        adventureGame.startGame();
    }

    private static ExpositionalTextNode start(Player player) {
        ExpositionalTextNode node = new ExpositionalTextNode("** First Adventure: The Room **");
        node.then(new FreeTextInputNode("What is your name?", (p, str) -> p.setName(String.valueOf(str))))
            .then(new ExpositionalTextNode("Hello, $PLAYER_NAME. You are in a room."))
            .then(new ExpositionalTextNode("You see a door."))
            .then(new ChoiceTextInputNode("Enter it?",
                                          new LinkedTextChoice("Yes", openTheDoor()),
                                          new LinkedTextChoice("No", boringEnd()),
                                          new LinkedTextChoice("Yes, but with style", openTheDoor(), p -> p.getName().contains("cool"))));
        return node;
    }

    private static ExpositionalTextNode boringEnd() {
        return new ExpositionalTextNode("Your life is boring.");
    }

    private static ExpositionalTextNode openTheDoor() {
        ExpositionalTextNode node = new ExpositionalTextNode("You open the door and enter a new room.");
        node.then(new ChoiceTextInputNode("In the new new room, you see a glowing orb. What do you do?",
                                          new LinkedTextChoice("Pick it up and put it in your backpack", pickUpTheOrb()),
                                          new LinkedTextChoice("Destroy it", destroyTheOrb()),
                                          new LinkedTextChoice("Leave it", boringEnd())));
        return node;
    }

    private static AcquireItemTextNode pickUpTheOrb() {
        AcquireItemTextNode node = new AcquireItemTextNode("You pick up the orb.", orb);
        node.then(leaveTheRoomChoice());
        return node;
    }

    private static AcquireEffectTextNode destroyTheOrb() {
        AcquireEffectTextNode node = new AcquireEffectTextNode("Destroying the orb released it's power, you feel hale and hearty.", superStrength);
        node.then(leaveTheRoomChoice());
        return node;
    }

    private static ChoiceTextInputNode leaveTheRoomChoice() {
        return new ChoiceTextInputNode("Would you like to leave the room, or stay for a while?",
                                       new LinkedTextChoice("Leave the room", leaveTheRoomEnding()),
                                       new LinkedTextChoice("Stay for a while", stayForAWhile()));
    }

    private static BranchNode leaveTheRoomEnding() {
        return new BranchNode(
                new LinkedTextChoice("You leave the room with a useless orb. Maybe some day you can sell it.", p -> p.hasItem(orb)),
                new LinkedTextChoice("You leave the room.")
        );
    }

    private static ExpositionalTextNode stayForAWhile() {
        ExpositionalTextNode node = new ExpositionalTextNode("You stay for a while.");
        node.then(new ExpositionalTextNode("After 14 days of staying in this room, you feel dehydrated, hungry, tired, and weak. You ask yourself why you stayed in this room for so long, it was a very stupid decision."))
            .then(new ExpositionalTextNode("Suddenly, an ogre enters the room."))
            .then(new ExpositionalTextNode("\"Hey! What are you doing in my room?\" shouts the Ogre. \"I'm going to eat you!\""))
            .then(
                    new ChoiceTextInputNode("What do you do?",
                                            new LinkedTextChoice("Throw the orb at the ogre", throwTheOrb(), p -> p.hasItem(orb)),
                                            new LinkedTextChoice("Stand up, raise your fists, and fight the ogre", fightTheOgre()),
                                            new LinkedTextChoice("Run away", leaveTheRoomEnding()),
                                            new LinkedTextChoice("Apologize and explain that you were just exploring", deathByOgre())
                    )
                );
        return node;
    }

    private static ExpositionalTextNode throwTheOrb() {
        ExpositionalTextNode node = new ExpositionalTextNode("You throw the orb at the ogre. In shatters upon impact, and a power is released. The ogre absorbs the power, and you feel very frightened.");
        node.then(new ExpositionalTextNode("The ogre smiles, and takes one step closer to you. You poop your pants in fear."))
            .then(new ExpositionalTextNode("The ogre extends a finger to touch you, and as he does, every bone in your body breaks. You scream in agony."))
            .then(new ExpositionalTextNode("The ogre laughs as you lay on the ground, unable to move. He picks you up and eats you. You die."));
        return node;
    }

    private static ExpositionalTextNode fightTheOgre() {
        ExpositionalTextNode node = new ExpositionalTextNode("The ogre laughs as you hobble over to him. You have very little energy because you are so exhausted.");
        node.then(
                new BranchNode(
                        new LinkedTextChoice("You throw the first punch, and the ogre is thrown back as if he was hit by a train. You are amazed at your newfound strength. It must be the orb...", null, p -> p.hasEffect(superStrength)),
                        new LinkedTextChoice("You throw the first punch.", deathByOgre())
                )
        );
        return node;
    }

    private static ExpositionalTextNode deathByOgre() {
        return new ExpositionalTextNode("The ogre doesn't care. He eats you, and you die.");
    }
}
