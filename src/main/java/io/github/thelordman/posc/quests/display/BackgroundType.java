package io.github.thelordman.posc.quests.display;

import org.bukkit.NamespacedKey;

public enum BackgroundType {

    GOLD_BLOCK("gold_block");

    private final NamespacedKey texture;

    BackgroundType(String texture) {
        this.texture = NamespacedKey.minecraft("textures/block/" + texture + ".png");
    }

    public NamespacedKey getTexture() {
        return texture;
    }

}