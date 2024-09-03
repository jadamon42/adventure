package com.github.jadamon42.adventure.builder.state;

import com.github.jadamon42.adventure.builder.element.NodeVisitor;
import com.github.jadamon42.adventure.builder.element.condition.ConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.condition.LinkedConditionalTextInput;
import com.github.jadamon42.adventure.builder.element.condition.LinkedTextChoiceInput;
import com.github.jadamon42.adventure.builder.node.*;
import com.github.jadamon42.adventure.builder.state.serialize.*;

import java.util.*;

public class MainBoardStateBuilder implements NodeVisitor {
    List<SerializableNode> mainStateChildren;

    public MainBoardStateBuilder() {
        mainStateChildren = new ArrayList<>();
    }

    public MainBoardState build() {
        return new MainBoardState(mainStateChildren);
    }

    @Override
    public void visit(AcquireEffectTextNode acquireEffectTextNode) {
        mainStateChildren.add(new SerializableAcquireEffectTextNode(
                acquireEffectTextNode.getId(),
                acquireEffectTextNode.getLayoutX(),
                acquireEffectTextNode.getLayoutY(),
                acquireEffectTextNode.getTitle(),
                acquireEffectTextNode.getText(),
                acquireEffectTextNode.getPreviousNodeConnectionIds(),
                acquireEffectTextNode.getNextNodeConnectionId(),
                acquireEffectTextNode.getEffectConnectionId()));
    }

    @Override
    public void visit(AcquireItemTextNode acquireItemTextNode) {
        mainStateChildren.add(new SerializableAcquireItemTextNode(
                acquireItemTextNode.getId(),
                acquireItemTextNode.getLayoutX(),
                acquireItemTextNode.getLayoutY(),
                acquireItemTextNode.getTitle(),
                acquireItemTextNode.getText(),
                acquireItemTextNode.getPreviousNodeConnectionIds(),
                acquireItemTextNode.getNextNodeConnectionId(),
                acquireItemTextNode.getItemConnectionId()));
    }

    @Override
    public void visit(And and) {
        mainStateChildren.add(new SerializableAnd(
                and.getId(),
                and.getLayoutX(),
                and.getLayoutY(),
                and.getTitle(),
                and.getCondition1ConnectionId(),
                and.getCondition2ConnectionId(),
                and.getConditionConnectionIds()));
    }

    @Override
    public void visit(BranchNode branchNode) {
        List<SerializableConditional> serializableConditionals = new ArrayList<>();
        for (LinkedConditionalTextInput branch : branchNode.getBranches()) {
            serializableConditionals.add(new SerializableConditional(
                    branch.getText(),
                    branch.getPromptText(),
                    branch.getConditionConnectionId(),
                    branch.getNextConnectionId(),
                    branch.isDefaulted()));
        }
        mainStateChildren.add(new SerializableBranchNode(
                branchNode.getId(),
                branchNode.getLayoutX(),
                branchNode.getLayoutY(),
                branchNode.getTitle(),
                branchNode.getPreviousNodeConnectionIds(),
                serializableConditionals));
    }

    @Override
    public void visit(ChoiceTextInputNode choiceTextInputNode) {
        List<SerializableConditional> serializableConditionals = new ArrayList<>();
        for (LinkedTextChoiceInput choice : choiceTextInputNode.getChoices()) {
            serializableConditionals.add(new SerializableConditional(
                    choice.getText(),
                    choice.getPromptText(),
                    choice.getConditionConnectionId(),
                    choice.getNextConnectionId(),
                    choice.isDefaulted()));
        }
        mainStateChildren.add(new SerializableChoiceTextInputNode(
                choiceTextInputNode.getId(),
                choiceTextInputNode.getLayoutX(),
                choiceTextInputNode.getLayoutY(),
                choiceTextInputNode.getTitle(),
                choiceTextInputNode.getText(),
                choiceTextInputNode.getPreviousNodeConnectionIds(),
                serializableConditionals));
    }

    @Override
    public void visit(Effect effect) {
        mainStateChildren.add(new SerializableEffect(
                effect.getId(),
                effect.getLayoutX(),
                effect.getLayoutY(),
                effect.getTitle(),
                effect.getEffectConnectionIds()));
    }

    @Override
    public void visit(EffectCondition effectCondition) {
        mainStateChildren.add(new SerializableEffectCondition(
                effectCondition.getId(),
                effectCondition.getLayoutX(),
                effectCondition.getLayoutY(),
                effectCondition.getTitle(),
                effectCondition.getEffectConnectionId(),
                effectCondition.getConditionConnectionIds()));
    }

    @Override
    public void visit(ExpositionalTextNode expositionalTextNode) {
        mainStateChildren.add(new SerializableExpositionalTextNode(
                expositionalTextNode.getId(),
                expositionalTextNode.getLayoutX(),
                expositionalTextNode.getLayoutY(),
                expositionalTextNode.getTitle(),
                expositionalTextNode.getText(),
                expositionalTextNode.getPreviousNodeConnectionIds(),
                expositionalTextNode.getNextNodeConnectionId()));
    }

    @Override
    public void visit(FreeTextInputNode freeTextInputNode) {
        mainStateChildren.add(new SerializableFreeTextInputNode(
                freeTextInputNode.getId(),
                freeTextInputNode.getLayoutX(),
                freeTextInputNode.getLayoutY(),
                freeTextInputNode.getTitle(),
                freeTextInputNode.getText(),
                freeTextInputNode.getPreviousNodeConnectionIds(),
                freeTextInputNode.getNextNodeConnectionId(),
                freeTextInputNode.getInputHandlerConnectionId()));
    }

    @Override
    public void visit(InputHandler inputHandler) {
        mainStateChildren.add(new SerializableInputHandler(
                inputHandler.getId(),
                inputHandler.getLayoutX(),
                inputHandler.getLayoutY(),
                inputHandler.getTitle(),
                inputHandler.getSubtype(),
                inputHandler.getInputHandlerConnectionIds()));
    }

    @Override
    public void visit(Item item) {
        mainStateChildren.add(new SerializableItem(
                item.getId(),
                item.getLayoutX(),
                item.getLayoutY(),
                item.getTitle(),
                item.getItemConnectionIds()));
    }

    @Override
    public void visit(ItemCondition itemCondition) {
        mainStateChildren.add(new SerializableItemCondition(
                itemCondition.getId(),
                itemCondition.getLayoutX(),
                itemCondition.getLayoutY(),
                itemCondition.getTitle(),
                itemCondition.getItemConnectionId(),
                itemCondition.getConditionConnectionIds()));
    }

    @Override
    public void visit(NameCondition nameCondition) {
        mainStateChildren.add(new SerializableNameCondition(
                nameCondition.getId(),
                nameCondition.getLayoutX(),
                nameCondition.getLayoutY(),
                nameCondition.getTitle(),
                nameCondition.getText(),
                nameCondition.getSubtype(),
                nameCondition.getConditionConnectionIds()));
    }

    @Override
    public void visit(Or or) {
        mainStateChildren.add(new SerializableOr(
                or.getId(),
                or.getLayoutX(),
                or.getLayoutY(),
                or.getTitle(),
                or.getCondition1ConnectionId(),
                or.getCondition2ConnectionId(),
                or.getConditionConnectionIds()));
    }

    @Override
    public void visit(Start start) {
        mainStateChildren.add(new SerializableStart(
                start.getId(),
                start.getLayoutX(),
                start.getLayoutY(),
                "Start",
                start.getNextConnectionId()));
    }

    @Override
    public void visit(SwitchNode switchNode) {
        List<SerializableConditional> serializableConditionals = new ArrayList<>();
        for (ConditionalTextInput case_ : switchNode.getCases()) {
            serializableConditionals.add(new SerializableConditional(
                    case_.getText(),
                    case_.getPromptText(),
                    case_.getConditionConnectionId(),
                    case_.getNextConnectionId(),
                    case_.isDefaulted()));
        }
        mainStateChildren.add(new SerializableSwitchNode(
                switchNode.getId(),
                switchNode.getLayoutX(),
                switchNode.getLayoutY(),
                switchNode.getTitle(),
                switchNode.getPreviousNodeConnectionIds(),
                switchNode.getNextNodeConnectionId(),
                serializableConditionals));
    }
}
