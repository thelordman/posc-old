package io.github.thelordman.costrength.quests.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.github.thelordman.costrength.quests.display.FrameType;

import java.lang.reflect.Type;

public class FrameTypeAdapter implements JsonSerializer<FrameType> {

    @Override
    public JsonElement serialize(FrameType src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getName());
    }

}
