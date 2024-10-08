package com.github.jadamon42.adventure.common.util;

import com.github.jadamon42.adventure.common.node.*;

import java.util.HashMap;
import java.util.UUID;

public class StoryNodeTraverser implements StoryNodeVisitor {
    private final HashMap<UUID, StoryNode> storyNodeMap;

    public StoryNodeTraverser() {
        storyNodeMap = new HashMap<>();
    }

    public HashMap<UUID, StoryNode> getStoryNodeMap(StoryNode startNode) {
        startNode.accept(this);
        return storyNodeMap;
    }

    private void visitNext(Linkable linkable) {
        if (linkable.getNextNode() != null) {
            linkable.getNextNode().accept(this);
        }
    }

    @Override
    public void visit(ExpositionalTextNode node) {
        storyNodeMap.put(node.getId(), node);
        visitNext(node);
    }

    @Override
    public void visit(ChoiceTextInputNode node) {
        storyNodeMap.put(node.getId(), node);
        for (LinkedConditionalText choice : node.getAllChoices()) {
            visitNext(choice);
        }
    }

    @Override
    public void visit(FreeTextInputNode node) {
        storyNodeMap.put(node.getId(), node);
        visitNext(node);

    }

    @Override
    public void visit(AcquireItemTextNode node) {
        storyNodeMap.put(node.getId(), node);
        visitNext(node);
    }

    @Override
    public void visit(AcquireEffectTextNode node) {
        storyNodeMap.put(node.getId(), node);
        visitNext(node);
    }

    @Override
    public void visit(BranchNode node) {
        storyNodeMap.put(node.getId(), node);
        for (LinkedConditionalText choice : node.getAllBranches()) {
            visitNext(choice);
        }
    }

    @Override
    public void visit(SwitchNode node) {
        storyNodeMap.put(node.getId(), node);
        visitNext(node);
    }

    @Override
    public void visit(WaitNode node) {
        storyNodeMap.put(node.getId(), node);
        visitNext(node);
    }
}
