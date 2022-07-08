package io.github.thelordman.costrength.quests.trigger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.thelordman.costrength.quests.data.ItemData;

import java.util.function.Consumer;

public class ShotCrossbow implements Trigger {

    @Expose
    @SerializedName("item")
    private ItemData item;

    public void setItem(Consumer<ItemData> consumer) {
        this.item = new ItemData();
        consumer.accept(item);
    }

}
