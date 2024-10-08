package com.github.jadamon42.adventure.builder.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jadamon42.adventure.builder.state.serialize.SerializableNode;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.List;


@JsonSerialize
@JsonDeserialize
public class MainBoardState {
    private final List<SerializableNode> mainStateChildren;

    @JsonCreator
    MainBoardState(@JsonProperty("mainStateChildren") List<SerializableNode> mainStateChildren) {
        this.mainStateChildren = mainStateChildren;
    }

    @JsonProperty("mainStateChildren")
    List<SerializableNode> getMainStateChildren() {
        return mainStateChildren;
    }

    public void applyTo(Pane mainBoard) {
        MainBoardStateApplier applier = new MainBoardStateApplier(mainBoard, this);
        Platform.runLater(applier::apply);
    }
}
