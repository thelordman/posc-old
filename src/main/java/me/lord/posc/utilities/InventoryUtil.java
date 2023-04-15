package me.lord.posc.utilities;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {
    public static void remove(Inventory inventory, Material material, int amount) {
        ItemStack[] contents = inventory.getStorageContents();

        for (ItemStack item : contents) {
            if (item.getType() != material) continue;

            if (item.getAmount() >= amount) {
                item.subtract(amount);
                break;
            } else {
                amount -= item.getAmount();
                item.setAmount(0);
            }
        }

        inventory.setStorageContents(contents);
    }
}
