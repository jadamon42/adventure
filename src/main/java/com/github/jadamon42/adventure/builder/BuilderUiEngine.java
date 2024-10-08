package com.github.jadamon42.adventure.builder;

import com.github.jadamon42.adventure.builder.node.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuilderUiEngine {
    private BuilderUiController controller;

    public void initialize(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/buildui.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.setTitle("Adventure Builder");
        stage.show();

        controller.addDraggableNodeButtonToStory(ExpositionalTextNode.class);
        controller.addDraggableNodeButtonToStory(WaitNode.class);
        controller.addDraggableNodeButtonToStory(ChoiceTextInputNode.class);
        controller.addDraggableNodeButtonToStory(FreeTextInputNode.class);
        controller.addDraggableNodeButtonToStory(AcquireEffectTextNode.class);
        controller.addDraggableNodeButtonToStory(AcquireItemTextNode.class);
        controller.addDraggableNodeButtonToStory(BranchNode.class);
        controller.addDraggableNodeButtonToStory(SwitchNode.class);
        controller.addDraggableNodeButtonToConditions(ItemCondition.class);
        controller.addDraggableNodeButtonToConditions(EffectCondition.class);
        controller.addDraggableNodeButtonToConditions(NameCondition.class);
        controller.addDraggableNodeButtonToConditions(And.class);
        controller.addDraggableNodeButtonToConditions(Or.class);
        controller.addDraggableNodeButtonToModels(Item.class);
        controller.addDraggableNodeButtonToModels(Effect.class);
        controller.addDraggableNodeButtonToHandlers(InputHandler.class);

        controller.addStartNode();
    }
}
