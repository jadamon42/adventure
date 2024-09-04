package com.github.jadamon42.adventure.common.state.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.jadamon42.adventure.common.state.GameState;

import java.io.IOException;

public class GameStateSerializer extends JsonSerializer<GameState> {


    @Override
    public void serialize(GameState gameState, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        SerializableGameStateBuilder builder = new SerializableGameStateBuilder();
        jsonGenerator.writeObject(builder.build(gameState));
    }
}
