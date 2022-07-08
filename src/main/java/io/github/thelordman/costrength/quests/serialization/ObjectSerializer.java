package io.github.thelordman.costrength.quests.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.thelordman.costrength.quests.data.DimensionType;
import io.github.thelordman.costrength.quests.data.EffectType;
import io.github.thelordman.costrength.quests.data.PotionType;
import io.github.thelordman.costrength.quests.display.FrameType;
import io.github.thelordman.costrength.quests.utility.KeyValue;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.StructureType;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

public class ObjectSerializer {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeHierarchyAdapter(Material.class, new MaterialAdapter())
            .registerTypeHierarchyAdapter(EntityType.class, new EntityTypeAdapter())
            .registerTypeHierarchyAdapter(PotionType.class, new PotionTypeAdapter())
            .registerTypeHierarchyAdapter(DimensionType.class, new DimensionTypeAdapter())
            .registerTypeHierarchyAdapter(NamespacedKey.class, new NamespacedKeyAdapter())
            .registerTypeHierarchyAdapter(List.class, new ListAdapter())
            .registerTypeHierarchyAdapter(FrameType.class, new FrameTypeAdapter())
            .registerTypeHierarchyAdapter(Map.class, new MapAdapter())
            .registerTypeHierarchyAdapter(EffectType.class, new EffectTypeAdapter())
            .registerTypeHierarchyAdapter(KeyValue.class, new KeyValueAdapter())
            .registerTypeHierarchyAdapter(Biome.class, new BiomeAdapter())
            .registerTypeHierarchyAdapter(StructureType.class, new StructureTypeAdapter())
            .registerTypeHierarchyAdapter(GameMode.class, new GameModeAdapter())
            .create();

    public String serialize(Object object) {
        return GSON.toJson(object);
    }

}
