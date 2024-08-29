package com.github.jadamon42.adventure.builder.state;

import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.List;

public class MainBoardState implements Serializable {
    private final List<SerializableNode> mainStateChildren;

    MainBoardState(List<SerializableNode> mainStateChildren) {
        this.mainStateChildren = mainStateChildren;
    }

    List<SerializableNode> getMainStateChildren() {
        return mainStateChildren;
    }

    public void applyTo(Pane mainBoard) {
        MainBoardStateApplier applier = new MainBoardStateApplier(mainBoard, this);
        applier.apply();
    }
}
