package io.github.thelordman.costrength.quests.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.github.thelordman.costrength.quests.data.EffectType;

import java.lang.reflect.Type;

public class EffectTypeAdapter implements JsonSerializer<EffectType> {

    @Override
    public JsonElement serialize(EffectType src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getKey());
    }

}
