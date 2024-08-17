package com.github.jadamon42.adventure.model;

import com.github.jadamon42.adventure.node.*;
import com.github.jadamon42.adventure.engine.ui.JavaFXGameEngine;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiAdventureGameTest extends Application {

    private final static Effect superStrength = new Effect("super strength");
    private final static Item orb = new Item("orb");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Player player = new Player();
        JavaFXGameEngine gameEngine = new JavaFXGameEngine(player, start(player));
        gameEngine.initialize(stage);
        gameEngine.startGame();
//        gameEngine.loadGame("/Users/jdamon/Documents/Adventure/checkpoint.adch");
    }

    private static ExpositionalTextNode start(Player player) {
        ExpositionalTextNode node = new ExpositionalTextNode("** First Adventure: The Room **");
        node.then(new FreeTextInputNode("What is your name?", (p, str) -> p.setName(String.valueOf(str))))
            .then(playerNameAndExposition(player));
        return node;
    }

    private static SwitchNode playerNameAndExposition(Player player) {
        SwitchNode node = new SwitchNode(
                new TextChoice("$PLAYER_NAME? What a dumb name. Anyway, you are in a room (with a dumb name).",
                               p -> p.getName().equalsIgnoreCase("nick")
                                    || p.getName().equalsIgnoreCase("victor")
                                    || p.getName().equalsIgnoreCase("vector")
                                    || p.getName().equalsIgnoreCase("nicholas")
                                    || p.getName().equalsIgnoreCase("neck")
                                    || p.getName().equalsIgnoreCase("hannah")),
                new TextChoice("$PLAYER_NAME? What a cool name! Anyway, you are in a room (and everyone is probably jealous of how cool you are).", p -> p.getName().equalsIgnoreCase("jonathan") || p.getName().equalsIgnoreCase("jon")),
                new TextChoice("$PLAYER_NAME? You sound old. Anyway, you're in a room (and you're old).", p -> p.getName().equalsIgnoreCase("dave") || p.getName().equalsIgnoreCase("david") || p.getName().equalsIgnoreCase("natalie")),
                new TextChoice("Hello, $PLAYER_NAME. You are in a room.")
        );
        node.then(nameTag());
        return node;
    }

    private static ExpositionalTextNode boringEnd() {
        return new ExpositionalTextNode("Your life is boring.");
    }

    private static LinkableStoryTextNode nameTag() {
        LinkableStoryTextNode node = new ExpositionalTextNode("The room is empty, but you notice a name tag on a small table with a pen next to it.");
        node.then(new ChoiceTextInputNode("Would you like to put your name on the name tag and wear it?",
                                          new LinkedTextChoice("Yes", wearTheNameTag()),
                                          new LinkedTextChoice("No", leaveTheNameTag())));
        return node;
    }

    private static AcquireItemTextNode wearTheNameTag() {
        AcquireItemTextNode node = new AcquireItemTextNode("You write your name, $PLAYER_NAME, on the name tag, stick it on, and proudly wear it.", new Item("name tag"));
        node.then(new ExpositionalTextNode("With your name written across your chest, you scan the room for other goodies."))
            .then(lookAround());
        return node;
    }

    private static ExpositionalTextNode leaveTheNameTag() {
        ExpositionalTextNode node = new ExpositionalTextNode("You leave the name tag on the table, and look around the room.");
        node.then(lookAround());
        return node;
    }

    private static ExpositionalTextNode lookAround() {
        ExpositionalTextNode node = new ExpositionalTextNode("You see a door.");
        node.then(new ChoiceTextInputNode("Enter it?",
                      new LinkedTextChoice("Yes", openTheDoor()),
                      new LinkedTextChoice("No", boringEnd()),
                      new LinkedTextChoice("Yes, but with style", openTheDoor(), p -> p.getName().contains("cool"))));
        return node;
    }

    private static ExpositionalTextNode openTheDoor() {
        ExpositionalTextNode node = new ExpositionalTextNode("You open the door and enter a new room.");
        node.then(new ChoiceTextInputNode("In the new new room, you see a glowing orb. What do you do?",
                                          new LinkedTextChoice("Pick it up and put it in my backpack", pickUpTheOrb()),
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
            .then(new ExpositionalTextNode("\"Hey! What are you doing in my room?\" shouts the Ogre."))
            .then(dealWithOgre());
        return node;
    }

    private static BranchNode dealWithOgre() {
        return new BranchNode(
                new LinkedTextChoice("\"Wait...\" the Ogre interrupts himself \"Your name is $PLAYER_NAME? That is an Ogre name! Are you an Ogre too?\"", areYouAnOgre(), p -> p.getName().length() <= 5),
                new LinkedTextChoice("\"I will eat $PLAYER_NAME the human!\"", chooseWhatToDoWithOgre(), p -> p.hasItem("name tag")),
                new LinkedTextChoice("\"I'm going to eat you!\"", chooseWhatToDoWithOgre()));
    }

    private static ChoiceTextInputNode areYouAnOgre() {
        return new ChoiceTextInputNode(
                "What to do tell the Ogre?",
                new LinkedTextChoice("Yes, I am an Ogre", iAmAnOgre()),
                new LinkedTextChoice("No, I am not an Ogre. I am better. I am a human.", iAmAHuman())
        );
    }

    private static ExpositionalTextNode iAmAnOgre() {
        ExpositionalTextNode node = new ExpositionalTextNode("The Ogre looks confused. He scratches his head, and then says, \"You are a very small Ogre, and you are very ugly.\"");
        node.then(new ExpositionalTextNode("\"I feel bad for $PLAYER_NAME the micro Ogre. You were not fed enough humans as a young Ogre to grow big and strong like me.\""))
            .then(new ExpositionalTextNode("The Ogre pats you on the head, trying to console his new friend."))
            .then(new ExpositionalTextNode("\"I am sorry for you, $PLAYER_NAME. But I cannot help you, and I am tired of looking at you because your are so ugly. I am going to leave.\""))
            .then(new ExpositionalTextNode("The Ogre leaves the room, and you are alone. You decide to leave the room"))
            .then(leaveTheRoomEnding());
        return node;
    }

    private static ExpositionalTextNode iAmAHuman() {
        ExpositionalTextNode node = new ExpositionalTextNode("The Ogre laughs at you. \"Hahahahaha! What a stupid puny human!\"");
        node.then(new ExpositionalTextNode("\"I am going to eat you, $PLAYER_NAME the human.\""))
            .then(chooseWhatToDoWithOgre());
        return node;
    }

    private static ChoiceTextInputNode chooseWhatToDoWithOgre() {
        return new ChoiceTextInputNode("What do you do?",
                new LinkedTextChoice("Throw the orb at the ogre", throwTheOrb(), p -> p.hasItem(orb)),
                new LinkedTextChoice("Stand up, raise my fists, and fight the ogre", fightTheOgre()),
                new LinkedTextChoice("Run away", leaveTheRoomEnding()),
                new LinkedTextChoice("Apologize and explain that I was just exploring", deathByOgre())
        );
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
