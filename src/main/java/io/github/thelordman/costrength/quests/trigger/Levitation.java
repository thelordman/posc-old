package io.github.thelordman.costrength.quests.trigger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.thelordman.costrength.quests.data.DistanceData;
import io.github.thelordman.costrength.quests.data.Range;

import java.util.function.Consumer;

public class Levitation implements Trigger {

    @Expose
    @SerializedName("distance")
    private DistanceData distance;

    @Expose
    @SerializedName("duration")
    private Range<Integer> duration;

    public void setDistance(Consumer<DistanceData> consumer) {
        this.distance = new DistanceData();
        consumer.accept(distance);
    }

    public void setDuration(int value) {
        this.duration = new Range<>(value);
    }

}
