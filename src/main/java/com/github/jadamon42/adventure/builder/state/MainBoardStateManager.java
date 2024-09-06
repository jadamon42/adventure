package com.github.jadamon42.adventure.builder.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jadamon42.adventure.builder.state.serialize.*;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
            new NamedType(SerializableSwitchNode.class, "SerializableSwitchNode"),
            new NamedType(SerializableWaitNode.class, "SerializableWaitNode")
        );
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    public void saveGame(File saveFile, MainBoardState mainBoardState) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(saveFile);
             GZIPOutputStream gzipOut = new GZIPOutputStream(fileOut);
             OutputStreamWriter writer = new OutputStreamWriter(gzipOut)) {
            objectMapper.writeValue(writer, mainBoardState);
        }
    }

    public MainBoardState loadGame(File saveFile) throws IOException {
        try (FileInputStream fileIn = new FileInputStream(saveFile);
             GZIPInputStream gzipIn = new GZIPInputStream(fileIn);
             InputStreamReader reader = new InputStreamReader(gzipIn)) {
            return objectMapper.readValue(reader, MainBoardState.class);
        }
    }
}
