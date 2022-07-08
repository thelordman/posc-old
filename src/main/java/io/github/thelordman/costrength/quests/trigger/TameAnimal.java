package io.github.thelordman.costrength.quests.trigger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.thelordman.costrength.quests.data.EntityData;

import java.util.function.Consumer;

public class TameAnimal implements Trigger {

    @Expose
    @SerializedName("entity")
    private EntityData entity;

    public void setEntity(Consumer<EntityData> consumer) {
        this.entity = new EntityData();
        consumer.accept(entity);
    }
}
