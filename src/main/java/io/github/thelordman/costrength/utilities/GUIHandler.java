package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.economy.EconomyManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUIHandler {
    public static void registerInventories() {
        //Food Shop
        for (int i = 0; i < 10; i++) {
            Data.foodShopGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, "", 1, ""));
        }
        for (int i = 17; i < 27; i++) {
            Data.foodShopGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, "", 1, ""));
        }
        Data.foodShopGUI.setItem(10, quickItem(Material.BREAD, Methods.cStr("&fFresh Bread"), 1, Methods.cStr("&6Restores &f2.5 hunger &6and &f3 saturation&6."), "", Methods.cStr("&f$10 &7(1x) &8| &f$640 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.foodShopGUI.setItem(11, quickItem(Material.COOKED_CHICKEN, Methods.cStr("&cHot Wings"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f4.5 saturation&6."), Methods.cStr("&6Has a &f10% chance &6of giving &fHaste +1&6."), "", Methods.cStr("&f$50 &7(1x) &8| &f$3,200 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.foodShopGUI.setItem(12, quickItem(Material.COOKED_BEEF, Methods.cStr("&4Well Done Steak"), 1, Methods.cStr("&6Restores &f4 hunger &6and &f6.5 saturation&6."), "", Methods.cStr("&f$100 &7(1x) &8| &f$6,400 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.foodShopGUI.setItem(13, quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&f$1,000 &7(1x) &8| &f$64,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.foodShopGUI.setItem(16, quickItem(Material.CAULDRON, Methods.cStr("&2Kitchen"), 1, Methods.cStr("&6Enter the kitchen menu where you can modify beer.")));

        //Kitchen Menu
        for (int i = 0; i < 10; i++) {
            Data.kitchenGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, "", 1, ""));
        }
        for (int i = 17; i < 27; i++) {
            Data.kitchenGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, "", 1, ""));
        }
        Data.kitchenGUI.setItem(10, quickItem(Material.MELON, Methods.cStr("&2Melon"), 1, Methods.cStr("&6Restores &f+2 hunger &6after drinking."), "", Methods.cStr("&f$500 &7(1x) &8| &f$32,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.kitchenGUI.setItem(11, quickItem(Material.SWEET_BERRIES, Methods.cStr("&cBerries"), 1, Methods.cStr("&6Restores &f+7 saturation &6after drinking."), "", Methods.cStr("&f$1,000 &7(1x) &8| &f$64,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.kitchenGUI.setItem(12, quickItem(Material.SUGAR, Methods.cStr("&fSugar"), 1, Methods.cStr("&6Gives &fspeed 1 for 20 seconds &6after drinking."), "", Methods.cStr("&f$2,000 &7(1x) &8| &f$128,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.kitchenGUI.setItem(13, quickItem(Material.POTION, Methods.cStr("&eAlcohol"), 1, Methods.cStr("&6Gives &fstrength 1 for 20 seconds &6after drinking."), "", Methods.cStr("&f$4,000 &7(1x) &8| &f$256,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
    }

    public static ItemStack quickItem(final Material material, final String name, final int amount, final String... lore) {
        final ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(List.of(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static void openGUI(Inventory inventory, Player player) {
        if (inventory.equals(Data.foodShopGUI)) {
            if (EconomyManager.getLevel(player.getUniqueId()) > 25) {
                inventory.setItem(16, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
                inventory.setItem(13, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
            }
            player.openInventory(inventory);
        }
        if (inventory.equals(Data.kitchenGUI)) {
            if (EconomyManager.getLevel(player.getUniqueId()) > 50) {
                inventory.setItem(12, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
                inventory.setItem(13, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
            }
            player.openInventory(inventory);
        }
    }
}