package com.github.jadamon42.adventure.builder;

import com.github.jadamon42.adventure.builder.element.ExceptionContent;
import com.github.jadamon42.adventure.builder.element.ExpandableTextInput;
import com.github.jadamon42.adventure.builder.element.NodeIconButton;
import com.github.jadamon42.adventure.builder.state.MainBoardState;
import com.github.jadamon42.adventure.builder.element.ZoomableScrollPane;
import com.github.jadamon42.adventure.builder.node.*;
import com.github.jadamon42.adventure.builder.state.MainBoardStateManager;
import com.github.jadamon42.adventure.common.state.GameStateManager;
import com.github.jadamon42.adventure.common.state.Checkpoint;
import com.github.jadamon42.adventure.common.state.GameState;
import com.github.jadamon42.adventure.common.model.Player;
import com.github.jadamon42.adventure.common.node.StoryNode;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

    private Start start;

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
        start = new Start();
        AppState.getInstance().addChildToMainBoard(start);
        AppState.getInstance().centerNode(start);
    }

    private void addDraggableNodeButton(FlowPane box, Class<? extends Node> nodeClass) {
        NodeIconButton button = new NodeIconButton(nodeClass);
        button.setAlignment(Pos.BOTTOM_CENTER);

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
                AppState.getInstance().addChildToMainBoard(newNode);
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
    private void handleSave() {
        MainBoardState state = AppState.getInstance().getMainBoardState();
        File save = getSaveFileFromUser();
        if (save != null) {
            MainBoardStateManager manager = new MainBoardStateManager();
            try {
                manager.saveGame(save, state);
            } catch (IOException e) {
                alertError("Save Failed", "An error occurred while saving the adventure.", e);
            }
        }
    }

    @FXML
    private void handleLoad() {
        File save = getLoadFileFromUser();
        if (save != null) {
            mainBoard.getChildren().clear();
            MainBoardStateManager manager = new MainBoardStateManager();
            try {
                MainBoardState state = manager.loadGame(save);
                state.applyTo(mainBoard);
            } catch (IOException | NullPointerException e) {
                alertError("Load Failed", "An error occurred while loading the adventure.", e);
            }
            Platform.runLater(() -> {
                mainBoard.layout();
                for (javafx.scene.Node node : mainBoard.getChildren()) {
                    if (node instanceof Start startNode) {
                        start = startNode;
                    }
                }
            });
        }
    }

    @FXML
    private void handleExport() {
        StoryNode adventure = start.getAdventure();
        if (adventure != null) {
            try {
                export(adventure);
            } catch (IOException e) {
                alertError("Export Failed", "An error occurred while exporting the adventure.", e);
            }
        } else {
            alertInformation("Export Failed", "Adventure must have at least one node.");
        }
    }

    @FXML
    public void handleClear() {
        mainBoard.getChildren().clear();
        addStartNode();
    }

    private void export(StoryNode adventure) throws IOException {
        boolean invalidInputsFound = checkForInvalidInputs();
        if (invalidInputsFound) {
            return;
        }
        File save = getExportFileFromUser();
        if (save != null) {
            Checkpoint start = new Checkpoint(new Player(), adventure);
            GameState startingState = new GameState(start);
            GameStateManager manager = new GameStateManager();
            manager.saveGame(save, startingState);
        }
    }

    private void alertInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void alertError(String title, String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ExceptionContent exception = new ExceptionContent(e);
        alert.getDialogPane().setExpandableContent(exception);

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

    private boolean checkForInvalidInputs() {
        // return true if invalid inputs are found
        for (ExpandableTextInput node : AppState.getInstance().getInvalidInputs()) {
            AppState.getInstance().focusOnNode(node);
            alertInformation("Invalid Input", "Please correct the invalid input.");
            return true;
        }
        return false;
    }
}
