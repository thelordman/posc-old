package io.github.thelordman.costrength.quests.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.github.thelordman.costrength.quests.data.DimensionType;

import java.lang.reflect.Type;

public class DimensionTypeAdapter implements JsonSerializer<DimensionType> {

    @Override
    public JsonElement serialize(DimensionType src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getKey());
    }

}
