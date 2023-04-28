package me.lord.posc.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GUI {
    ItemStack[] getItems();

    InventoryType getType();

    default Inventory createInventory() {
        Inventory inventory;
        if (getType() == InventoryType.CHEST) {
            inventory = Bukkit.createInventory(null, getItems().length, getTitle());
        } else {
            inventory = Bukkit.createInventory(null, getType(), getTitle());
        }
        inventory.setContents(getItems());
        return inventory;
    }

    Component getTitle();

    default void open(Player player) {
        player.openInventory(createInventory());
    }
}
