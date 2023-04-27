package me.lord.posc.gui;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public abstract class ChestGUI implements GUI {
    protected final ItemStack[] items;

    public ChestGUI() {
        items = new ItemStack[getRows() * 9];
    }

    @Override
    public InventoryType getType() {
        return InventoryType.CHEST;
    }

    @Override
    public ItemStack[] getItems() {
        return items;
    }

    public abstract int getRows();
}
