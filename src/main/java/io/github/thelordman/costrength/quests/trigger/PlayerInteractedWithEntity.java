package io.github.thelordman.costrength.quests.trigger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.thelordman.costrength.quests.data.EntityData;
import io.github.thelordman.costrength.quests.data.ItemData;

import java.util.function.Consumer;

public class PlayerInteractedWithEntity implements Trigger {

    @Expose
    @SerializedName("item")
    private ItemData item;

    @Expose
    @SerializedName("entity")
    private EntityData entity;

    public void setItem(Consumer<ItemData> consumer) {
        this.item = new ItemData();
        consumer.accept(item);
    }

    public void setEntity(Consumer<EntityData> consumer) {
        this.entity = new EntityData();
        consumer.accept(entity);
    }

}
