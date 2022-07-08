package io.github.thelordman.costrength.quests.trigger;

import io.github.thelordman.costrength.quests.data.DistanceData;
import io.github.thelordman.costrength.quests.data.LocationData;

import java.util.function.Consumer;

public class NetherTravel implements Trigger {

    private LocationData entered;
    private LocationData exited;
    private DistanceData distance;

    public void setEntered(Consumer<LocationData> consumer) {
        this.entered = new LocationData();
        consumer.accept(entered);
    }

    public void setExited(Consumer<LocationData> consumer) {
        this.exited = new LocationData();
        consumer.accept(exited);
    }

    public void setDistance(Consumer<DistanceData> consumer) {
        this.distance = new DistanceData();
        consumer.accept(distance);
    }

}
