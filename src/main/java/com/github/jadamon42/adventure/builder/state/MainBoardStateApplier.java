package com.github.jadamon42.adventure.builder.state;

import com.github.jadamon42.adventure.builder.element.connection.ConnectionLine;
import com.github.jadamon42.adventure.builder.node.*;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

class MainBoardStateApplier implements SerializableNodeVisitor {
    private final Pane mainBoard;
    private final MainBoardState mainBoardState;
    private final Map<String, ConnectionLine> connectionLineMap;

    public MainBoardStateApplier(Pane mainBoard, MainBoardState mainBoardState) {
        this.mainBoard = mainBoard;
        this.mainBoardState = mainBoardState;
        this.connectionLineMap = new HashMap<>();
    }

    public void apply() {
        mainBoardState.getMainStateChildren().forEach(node -> node.accept(this));
        connectionLineMap.forEach((key, value) -> {
            value.init();
            mainBoard.getChildren().add(value);
            Platform.runLater(value::update);
        });
    }

    @Override
    public void visit(SerializableAcquireEffectTextNode serializableNode) {
        AcquireEffectTextNode node = new AcquireEffectTextNode();
        setFullyLinkedProperties(serializableNode, node);
        node.setText(serializableNode.text());
        if (serializableNode.effectConnectionId() != null) {
            node.setEffectConnection(getConnectionLine(serializableNode.effectConnectionId()));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableAcquireItemTextNode serializableNode) {
        AcquireItemTextNode node = new AcquireItemTextNode();
        setFullyLinkedProperties(serializableNode, node);
        node.setText(serializableNode.text());
        if (serializableNode.itemConnectionId() != null) {
            node.setItemConnection(getConnectionLine(serializableNode.itemConnectionId()));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableAnd serializableNode) {
        And node = new And();
        setBaseProperties(serializableNode, node);
        if (serializableNode.condition1ConnectionId() != null) {
            node.setCondition1Connection(getConnectionLine(serializableNode.condition1ConnectionId()));
        }
        if (serializableNode.condition2ConnectionId() != null) {
            node.setCondition2Connection(getConnectionLine(serializableNode.condition2ConnectionId()));
        }
        for(String connectionId : serializableNode.conditionConnectionIds()) {
            node.addConditionConnection(getConnectionLine(connectionId));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableBranchNode serializableNode) {
        BranchNode node = new BranchNode();
        setPropertiesWithPreviousLinks(serializableNode, node);
        int i = 0;
        for (SerializableOption branch : serializableNode.branches()) {
            node.setBranch(
                    i,
                    branch.promptText(),
                    branch.text(),
                    getConnectionLine(branch.nextConnectionId()),
                    getConnectionLine(branch.conditionConnectionId()),
                    branch.isDefault()
            );
            i++;
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableChoiceTextInputNode serializableNode) {
        ChoiceTextInputNode node = new ChoiceTextInputNode();
        setPropertiesWithPreviousLinks(serializableNode, node);
        node.setText(serializableNode.text());
        int i = 0;
        for (SerializableOption choice : serializableNode.choices()) {
            node.setChoice(
                    i,
                    choice.promptText(),
                    choice.text(),
                    getConnectionLine(choice.nextConnectionId()),
                    getConnectionLine(choice.conditionConnectionId()),
                    choice.isDefault()
            );
            i++;
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableEffect serializableNode) {
        Effect node = new Effect();
        setBaseProperties(serializableNode, node);
        for (String connectionId : serializableNode.effectConnectionIds()) {
            node.addEffectConnection(getConnectionLine(connectionId));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableEffectCondition serializableNode) {
        EffectCondition node = new EffectCondition();
        setBaseProperties(serializableNode, node);
        if (serializableNode.effectConnectionId() != null) {
            node.setEffectConnection(getConnectionLine(serializableNode.effectConnectionId()));
        }
        for (String connectionId : serializableNode.conditionConnectionIds()) {
            node.addConditionConnection(getConnectionLine(connectionId));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableExpositionalTextNode serializableNode) {
        ExpositionalTextNode node = new ExpositionalTextNode();
        setFullyLinkedProperties(serializableNode, node);
        node.setText(serializableNode.text());
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableFreeTextInputNode serializableNode) {
        FreeTextInputNode node = new FreeTextInputNode();
        setFullyLinkedProperties(serializableNode, node);
        node.setText(serializableNode.text());
        if (serializableNode.inputHandlerConnectionId() != null) {
            node.setInputHandlerConnection(getConnectionLine(serializableNode.inputHandlerConnectionId()));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableInputHandler serializableNode) {
        InputHandler node = new InputHandler();
        setBaseProperties(serializableNode, node);
        node.setSubtype(serializableNode.subtype());
        for (String connectionId : serializableNode.inputHandlerConnectionIds()) {
            node.addInputHandlerConnection(getConnectionLine(connectionId));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableItem serializableNode) {
        Item node = new Item();
        setBaseProperties(serializableNode, node);
        for (String connectionId : serializableNode.itemConnectionIds()) {
            node.addItemConnection(getConnectionLine(connectionId));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableItemCondition serializableNode) {
        ItemCondition node = new ItemCondition();
        setBaseProperties(serializableNode, node);
        if (serializableNode.itemConnectionId() != null) {
            node.setItemConnection(getConnectionLine(serializableNode.itemConnectionId()));
        }
        for (String connectionId : serializableNode.conditionConnectionIds()) {
            node.addConditionConnection(getConnectionLine(connectionId));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableNameCondition serializableNode) {
        NameCondition node = new NameCondition();
        setBaseProperties(serializableNode, node);
        node.setText(serializableNode.text());
        node.setSubtype(serializableNode.subtype());
        for (String connectionId : serializableNode.conditionConnectionIds()) {
            node.addConditionConnection(getConnectionLine(connectionId));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableOr serializableNode) {
        Or node = new Or();
        setBaseProperties(serializableNode, node);
        if (serializableNode.condition1ConnectionId() != null) {
            node.setCondition1Connection(getConnectionLine(serializableNode.condition1ConnectionId()));
        }
        if (serializableNode.condition2ConnectionId() != null) {
            node.setCondition2Connection(getConnectionLine(serializableNode.condition2ConnectionId()));
        }
        for(String connectionId : serializableNode.conditionConnectionIds()) {
            node.addConditionConnection(getConnectionLine(connectionId));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableStart serializableNode) {
        Start node = new Start();
        setBaseProperties(serializableNode, node);
        if (serializableNode.nextConnectionId() != null) {
            node.getHeader().setNextNodeConnection(getConnectionLine(serializableNode.nextConnectionId()));
        }
        mainBoard.getChildren().add(node);
    }

    @Override
    public void visit(SerializableSwitchNode serializableNode) {
        SwitchNode node = new SwitchNode();
        setFullyLinkedProperties(serializableNode, node);
        int i = 0;
        for (SerializableOption case_ : serializableNode.cases()) {
            node.setCase(
                    i,
                    case_.promptText(),
                    case_.text(),
                    getConnectionLine(case_.conditionConnectionId()),
                    case_.isDefault()
            );
            i++;
        }
        mainBoard.getChildren().add(node);
    }

    private void setBaseProperties(SerializableNode serializableNode, Node node) {
        node.setId(serializableNode.id());
        node.setLayoutX(serializableNode.layoutX());
        node.setLayoutY(serializableNode.layoutY());
        node.setTitle(serializableNode.title());
    }

    private void setPropertiesWithPreviousLinks(SerializableNodeWithPreviousLinks serializableNode, BasicNode node) {
        setBaseProperties(serializableNode, node);
        for (String connectionId : serializableNode.previousConnectionIds()) {
            node.getHeader().addPreviousNodeConnection(getConnectionLine(connectionId));
        }
    }

    private void setFullyLinkedProperties(SerializableFullyLinkableNode serializableNode, BasicNode node) {
        setPropertiesWithPreviousLinks(serializableNode, node);
        if (serializableNode.nextConnectionId() != null) {
            node.getHeader().setNextNodeConnection(getConnectionLine(serializableNode.nextConnectionId()));
        }
    }

    private ConnectionLine getConnectionLine(String connectionId) {
        if (connectionId != null && !connectionLineMap.containsKey(connectionId)) {
            connectionLineMap.put(connectionId, new ConnectionLine());
        }
        return connectionLineMap.get(connectionId);
    }
}
