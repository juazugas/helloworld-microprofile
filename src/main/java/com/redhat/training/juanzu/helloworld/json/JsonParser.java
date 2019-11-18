package com.redhat.training.juanzu.helloworld.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

public interface JsonParser<T> {

    default T parse (String json) {
        if (null == json || json.trim().equals("")) {
            return null;
        }
        InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stream);
        JsonObject object = reader.readObject();
        reader.close();

        return doParse(object);
    }

    T doParse (JsonObject object);

}
