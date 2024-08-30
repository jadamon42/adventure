package com.github.jadamon42.adventure.builder;

import com.github.jadamon42.adventure.builder.element.NodeIconButton;
import com.github.jadamon42.adventure.builder.state.MainBoardState;
import com.github.jadamon42.adventure.builder.element.ZoomableScrollPane;
import com.github.jadamon42.adventure.builder.element.AppState;
import com.github.jadamon42.adventure.builder.node.*;
import com.github.jadamon42.adventure.builder.state.MainBoardStateManager;
import com.github.jadamon42.adventure.engine.GameStateManager;
import com.github.jadamon42.adventure.model.Checkpoint;
import com.github.jadamon42.adventure.model.GameState;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.node.StoryNode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class BuilderUiController {

    @FXML
    public ZoomableScrollPane zoomableScrollPane;

    @FXML
    private Pane mainBoard;

    @FXML
    private FlowPane storyDriversBox;

    @FXML
    private FlowPane conditionsBox;

    @FXML
    private FlowPane modelsBox;

    @FXML
    private FlowPane handlersBox;

    @FXML
    public void initialize() {
        zoomableScrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (!(event.getTarget() instanceof TextField)) {
                mainBoard.requestFocus();
            }
        });

        mainBoard.setStyle("-fx-background-color: lightgray;");
        zoomableScrollPane.setTarget(mainBoard);

        AppState.getInstance().setMainBoard(mainBoard);
        AppState.getInstance().setZoomableScrollPane(zoomableScrollPane);

        addDraggableNodeButton(storyDriversBox, ChoiceTextInputNode.class);
        addDraggableNodeButton(storyDriversBox, FreeTextInputNode.class);
        addDraggableNodeButton(storyDriversBox, AcquireEffectTextNode.class);
        addDraggableNodeButton(storyDriversBox, AcquireItemTextNode.class);
        addDraggableNodeButton(storyDriversBox, BranchNode.class);
        addDraggableNodeButton(storyDriversBox, SwitchNode.class);
        addDraggableNodeButton(storyDriversBox, ExpositionalTextNode.class);
        addDraggableNodeButton(conditionsBox, ItemCondition.class);
        addDraggableNodeButton(conditionsBox, EffectCondition.class);
        addDraggableNodeButton(conditionsBox, NameCondition.class);
        addDraggableNodeButton(conditionsBox, And.class);
        addDraggableNodeButton(conditionsBox, Or.class);
        addDraggableNodeButton(modelsBox, Item.class);
        addDraggableNodeButton(modelsBox, Effect.class);
        addDraggableNodeButton(handlersBox, InputHandler.class);

        addStartNode();
    }

    private void addStartNode() {
        Start start = Start.getInstance();
        mainBoard.getChildren().add(start);
        AppState.getInstance().centerNode(start);
    }

    private void addDraggableNodeButton(FlowPane box, Class<? extends Node> nodeClass) {
        NodeIconButton button = new NodeIconButton(nodeClass);

        mainBoard.setOnDragOver(event -> {
            if (event.getGestureSource() != mainBoard && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        mainBoard.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                Node newNode = NodeFactory.createNode(db.getString());
                WritableImage snapshot = NodeFactory.getNodeSnapshot(db.getString());
                mainBoard.getChildren().add(newNode);
                double centerX = event.getX() - snapshot.getWidth() / 2;
                double centerY = event.getY() - snapshot.getHeight() / 2;
                newNode.setLayoutX(centerX);
                newNode.setLayoutY(centerY);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        box.getChildren().add(button);
    }

    @FXML
    private void handleSave() throws IOException {
        MainBoardState state = AppState.getInstance().getMainBoardState();
        File save = getSaveFileFromUser();
        if (save != null) {
            MainBoardStateManager manager = new MainBoardStateManager();
            manager.saveGame(save, state);
        }
    }

    @FXML
    private void handleLoad() throws IOException {
        File save = getLoadFileFromUser();
        if (save != null) {
            mainBoard.getChildren().clear();
            MainBoardStateManager manager = new MainBoardStateManager();
            MainBoardState state = manager.loadGame(save);
            state.applyTo(mainBoard);
            mainBoard.layout();
            for (javafx.scene.Node node : mainBoard.getChildren()) {
                if (node instanceof Start start) {
                    Start.setInstance(start);
                }
            }
        }
    }

    @FXML
    private void handleExport() throws IOException {
        StoryNode adventure = Start.getInstance().getAdventure();
        if (adventure != null) {
            export(adventure);
        } else {
            alertUser("Export Failed", "Adventure must have at least one node.");
        }
    }

    @FXML
    public void handleClear() {
        mainBoard.getChildren().clear();
        addStartNode();
    }

    private void export(StoryNode adventure) throws IOException {
        File save = getExportFileFromUser();
        if (save != null) {
            Checkpoint start = new Checkpoint(new Player(), adventure);
            GameState startingState = new GameState(start);
            GameStateManager manager = new GameStateManager();
            manager.saveGame(save, startingState);
        }
    }

    private void alertUser(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private File getSaveFileFromUser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Adventure");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Adventure Builder Files", "*.advb"));
        return fileChooser.showSaveDialog(new Stage());
    }

    private File getLoadFileFromUser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Adventure");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Adventure Builder Files", "*.advb"));
        return fileChooser.showOpenDialog(new Stage());
    }

    private File getExportFileFromUser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Adventure");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Adventure Files", "*.adv"));
        return fileChooser.showSaveDialog(new Stage());
    }
}
