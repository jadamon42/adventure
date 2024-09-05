package com.github.jadamon42.adventure.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.*;

/*
 * WARNING
 * `BooleanFunction<T>` gets serialized to a string during JSON serialization.
 * Changes to this class will break compatibility with existing save files.
 */
@JsonSerialize(using = BooleanFunction.Serializer.class)
@JsonDeserialize(using = BooleanFunction.Deserializer.class)
public interface BooleanFunction<T> extends SerializableFunction<T, Boolean> {
    default BooleanFunction<T> and(BooleanFunction<T> other) {
        return (T t) -> this.apply(t) && other.apply(t);
    }

    default BooleanFunction<T> or(BooleanFunction<T> other) {
        return (T t) -> this.apply(t) || other.apply(t);
    }

    class Serializer extends JsonSerializer<BooleanFunction<?>> {
        @Override
        public void serialize(BooleanFunction<?> serializableLogic, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                outputStream.writeObject(serializableLogic);
                jsonGenerator.writeBinary(byteArrayOutputStream.toByteArray());
            }
        }
    }

    class Deserializer extends JsonDeserializer<BooleanFunction<?>> {
        @Override
        public BooleanFunction<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            byte[] value = jsonParser.getBinaryValue();
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(value);
                 ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream)) {
                return (BooleanFunction<?>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException(e);
            }
        }
    }
}
