package io.github.thelordman.costrength.quests.trigger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.thelordman.costrength.quests.data.DamageData;

import java.util.function.Consumer;

public class EntityHurtPlayer implements Trigger {

    @Expose
    @SerializedName("damage")
    private DamageData damage;

    /**
     * Configuration damage settings
     *
     * @param consumer Consumer for damage configuration
     */
    public void setDamage(Consumer<DamageData> consumer) {
        this.damage = new DamageData();
        consumer.accept(damage);
    }

}
