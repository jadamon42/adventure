package com.github.jadamon42.adventure.builder.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jadamon42.adventure.builder.state.serialize.*;

import java.io.*;

public class MainBoardStateManager {
    private final static ObjectMapper objectMapper;
    private final static SimpleModule module;

    static {
        module = new SimpleModule();
        module.registerSubtypes(
            new NamedType(SerializableAcquireEffectTextNode.class, "SerializableAcquireEffectTextNode"),
            new NamedType(SerializableAcquireItemTextNode.class, "SerializableAcquireItemTextNode"),
            new NamedType(SerializableAnd.class, "SerializableAnd"),
            new NamedType(SerializableBranchNode.class, "SerializableBranchNode"),
            new NamedType(SerializableChoiceTextInputNode.class, "SerializableChoiceTextInputNode"),
            new NamedType(SerializableEffect.class, "SerializableEffect"),
            new NamedType(SerializableEffectCondition.class, "SerializableEffectCondition"),
            new NamedType(SerializableExpositionalTextNode.class, "SerializableExpositionalTextNode"),
            new NamedType(SerializableFreeTextInputNode.class, "SerializableFreeTextInputNode"),
            new NamedType(SerializableInputHandler.class, "SerializableInputHandler"),
            new NamedType(SerializableItem.class, "SerializableItem"),
            new NamedType(SerializableItemCondition.class, "SerializableItemCondition"),
            new NamedType(SerializableNameCondition.class, "SerializableNameCondition"),
            new NamedType(SerializableOr.class, "SerializableOr"),
            new NamedType(SerializableStart.class, "SerializableStart"),
            new NamedType(SerializableSwitchNode.class, "SerializableSwitchNode")
        );
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    public void saveGame(File saveFile, MainBoardState mainBoardState) throws IOException {
        objectMapper.writeValue(saveFile, mainBoardState);
    }

    public MainBoardState loadGame(File saveFile) throws IOException {
        return objectMapper.readValue(saveFile, MainBoardState.class);
    }
}
