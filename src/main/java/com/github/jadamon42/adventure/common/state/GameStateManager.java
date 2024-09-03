package com.github.jadamon42.adventure.common.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jadamon42.adventure.common.state.serialize.*;

import java.io.*;

public class GameStateManager {
    private final static ObjectMapper objectMapper;
    private final static SimpleModule module;

    static {
        module = new SimpleModule();
        module.registerSubtypes(
                new NamedType(SerializableAcquireItemTextNode.class, "SerializedAcquireItemTextNode"),
                new NamedType(SerializableAcquireEffectTextNode.class, "SerializedAcquireEffectTextNode"),
                new NamedType(SerializableBranchNode.class, "SerializedBranchNode"),
                new NamedType(SerializableChoiceTextInputNode.class, "SerializedChoiceTextInputNode"),
                new NamedType(SerializableExpositionalTextNode.class, "SerializedExpositionalTextNode"),
                new NamedType(SerializableFreeTextInputNode.class, "SerializedFreeTextInputNode"),
                new NamedType(SerializableSwitchNode.class, "SerializedSwitchNode")
        );
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    public void saveGame(String saveFile, GameState gameState) throws IOException {
        File file = new File(saveFile);
        saveGame(file, gameState);
    }

    public GameState loadGame(String saveFile) throws IOException, ClassNotFoundException {
        File file = new File(saveFile);
        return loadGame(file);
    }

    public void saveGame(File saveFile, GameState gameState) throws IOException {
        objectMapper.writeValue(saveFile, gameState);
    }

    public GameState loadGame(File saveFile) throws IOException {
        return objectMapper.readValue(saveFile, GameState.class);
    }
}

