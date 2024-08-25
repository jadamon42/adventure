package com.github.jadamon42.adventure.builder;

import com.github.jadamon42.adventure.builder.element.ZoomableScrollPane;
import com.github.jadamon42.adventure.builder.element.AppState;
import com.github.jadamon42.adventure.builder.node.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
        nodes.add(new Start());
        nodes.add(new ExpositionalTextNode());
        nodes.add(new AcquireEffectTextNode());
        nodes.add(new AcquireItemTextNode());
        nodes.add(new BranchNode());
        nodes.add(new ChoiceTextInputNode());
        nodes.add(new FreeTextInputNode());
        nodes.add(new SwitchNode());
        nodes.add(new Effect());
        nodes.add(new Item());
        nodes.add(new And());
        nodes.add(new Or());
        nodes.add(new EffectCondition());
        nodes.add(new ItemCondition());
        nodes.add(new NameCondition());
        nodes.add(new InputHandler());
        addNodeButton.setOnMouseClicked(mouseEvent -> addNode());
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
    private void handleExport() {
        // Implement export functionality
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
