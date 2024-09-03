package com.github.jadamon42.adventure.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jadamon42.adventure.model.Player;
import com.github.jadamon42.adventure.model.PlayerDelta;

import java.io.*;
import java.util.function.BiFunction;

@JsonSerialize(using = PlayerDeltaBiFunction.Serializer.class)
@JsonDeserialize(using = PlayerDeltaBiFunction.Deserializer.class)
public interface PlayerDeltaBiFunction<T> extends BiFunction<Player, T, PlayerDelta>, Serializable {
    class Serializer extends JsonSerializer<PlayerDeltaBiFunction<?>> {
        @Override
        public void serialize(PlayerDeltaBiFunction<?> serializableLogic, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                outputStream.writeObject(serializableLogic);
                jsonGenerator.writeBinary(byteArrayOutputStream.toByteArray());
            }
        }
    }

    class Deserializer extends JsonDeserializer<PlayerDeltaBiFunction<?>> {
        @Override
        public PlayerDeltaBiFunction<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            byte[] value = jsonParser.getBinaryValue();
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(value);
                 ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream)) {
                return (PlayerDeltaBiFunction<?>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException(e);
            }
        }
    }
}
