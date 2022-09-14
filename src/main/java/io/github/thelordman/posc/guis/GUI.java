package io.github.thelordman.posc.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GUI implements Serializable {
    private final String name;
    private final int size;
    private final HashMap<Integer, byte[]> items = new HashMap<>();

    public GUI(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public void putItem(Integer index, ItemStack item) {
        items.put(index, item.serializeAsBytes());
    }

    public ItemStack getItem(Integer index) {
        return ItemStack.deserializeBytes(items.get(index));
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (Map.Entry<Integer, byte[]> item : items.entrySet()) {
            inventory.setItem(item.getKey(), ItemStack.deserializeBytes(item.getValue()));
        }
        player.openInventory(inventory);
    }
}