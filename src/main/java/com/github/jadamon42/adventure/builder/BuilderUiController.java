package com.github.jadamon42.adventure.builder;

import com.github.jadamon42.adventure.builder.element.connection.ConnectionManager;
import com.github.jadamon42.adventure.builder.element.ZoomableScrollPane;
import com.github.jadamon42.adventure.builder.element.AppState;
import com.github.jadamon42.adventure.builder.node.*;
import com.github.jadamon42.adventure.engine.GameStateManager;
import com.github.jadamon42.adventure.model.Checkpoint;
import com.github.jadamon42.adventure.model.GameState;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.node.StoryNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class BuilderUiController {

    @FXML
    public ZoomableScrollPane zoomableScrollPane;

    @FXML
    private Pane mainBoard;

    @FXML
    public Button addNodeButton;

    private LinkedList<Node> nodes;

    @FXML
    public void initialize() {
        zoomableScrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (!(event.getTarget() instanceof TextField)) {
                mainBoard.requestFocus();
            }
        });

        mainBoard.setStyle("-fx-background-color: lightgray;");
        zoomableScrollPane.setTarget(mainBoard);

        AppState.getInstance().setMainBoardBounds(mainBoard.getLayoutBounds());

        nodes = new LinkedList<>();
        nodes.add(Start.getInstance());
        nodes.add(new ExpositionalTextNode());
        nodes.add(new AcquireEffectTextNode());
//        nodes.add(new AcquireItemTextNode());
        nodes.add(new BranchNode());
//        nodes.add(new ChoiceTextInputNode());
        nodes.add(new FreeTextInputNode());
//        nodes.add(new SwitchNode());
        nodes.add(new Effect());
//        nodes.add(new Item());
//        nodes.add(new And());
//        nodes.add(new Or());
//        nodes.add(new ItemCondition());
        nodes.add(new EffectCondition());
        nodes.add(new NameCondition());
        nodes.add(new InputHandler());
        addNodeButton.setOnMouseClicked(mouseEvent -> addNode());

        ConnectionManager.getInstance().setCommonParent(mainBoard);
    }

    @FXML
    private void handleSave() {
        // Implement save functionality
    }

    @FXML
    private void handleLoad() {
        // Implement load functionality
    }

    @FXML
    private void handleExport() throws IOException {
        File save = getSaveFileFromUser();
        StoryNode adventure = Start.getInstance().getAdventure();
        Checkpoint start = new Checkpoint(new Player(), adventure);
        GameState startingState = new GameState(start);
        GameStateManager manager = new GameStateManager();
        manager.saveGame(save, startingState);
    }

    private File getSaveFileFromUser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Adventure");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Adventure Files", "*.adv"));
        return fileChooser.showSaveDialog(new Stage());
    }

    @FXML
    private void addNode() {
        Node node = nodes.pop();
        mainBoard.getChildren().add(node);
        node.setLayoutX((mainBoard.getWidth() - node.getWidth()) / 2);
        node.setLayoutY((mainBoard.getHeight() - node.getHeight()) / 2);
        mainBoard.layout();
    }
}
