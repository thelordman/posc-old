package io.github.thelordman.costrength.quests.trigger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.thelordman.costrength.quests.data.ItemData;
import org.bukkit.Material;

import java.util.function.Consumer;

public class ConsumeItem implements Trigger {

    @Expose
    @SerializedName("item")
    private ItemData item;

    /**
     * Set required consumed item using predicate
     * @param consumer Predicate for item
     */
    public void setItem(Consumer<ItemData> consumer) {
        this.item = new ItemData();
        consumer.accept(this.item);
    }

    /**
     * Set required consume item type
     * @param material Material of item
     */
    public void setItem(Material material) {
        setItem(item -> item.setType(material));
    }

}
