package com.github.jadamon42.adventure.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jadamon42.adventure.model.GameState;
import com.github.jadamon42.adventure.serialize.*;

import java.io.*;

public class GameStateManager {
    private final static ObjectMapper objectMapper;
    private final static SimpleModule module;

    static {
        module = new SimpleModule();
        module.registerSubtypes(
                new NamedType(SerializedAcquireItemTextNode.class, "SerializedAcquireItemTextNode"),
                new NamedType(SerializedAcquireEffectTextNode.class, "SerializedAcquireEffectTextNode"),
                new NamedType(SerializedBranchNode.class, "SerializedBranchNode"),
                new NamedType(SerializedChoiceTextInputNode.class, "SerializedChoiceTextInputNode"),
                new NamedType(SerializedExpositionalTextNode.class, "SerializedExpositionalTextNode"),
                new NamedType(SerializedFreeTextInputNode.class, "SerializedFreeTextInputNode"),
                new NamedType(SerializedSwitchNode.class, "SerializedSwitchNode")
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

