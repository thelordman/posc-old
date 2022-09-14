package io.github.thelordman.posc.food.data;

import java.io.Serializable;

public class KitchenBlockData implements Serializable {
    private final int levelRequirement;

    public KitchenBlockData(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }
}