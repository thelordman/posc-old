package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
        Data.foodShopGUI.setItem(14, quickItem(Material.RABBIT_STEW, Methods.cStr("&3Thick Stew"), 1, Methods.cStr("&6Restores &f20 hunger &6and &f20 saturation&6."), "", Methods.cStr("&f$5,000 &7(1x) &8| &f$320,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.foodShopGUI.setItem(16, quickItem(Material.CAULDRON, Methods.cStr("&2Kitchen"), 1, Methods.cStr("&6Enter the kitchen menu where you can buy ingredients for Beer.")));

        //Kitchen Menu
        for (int i = 0; i < 10; i++) {
            Data.kitchenGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        }
        for (int i = 17; i < 27; i++) {
            Data.kitchenGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        }
        Data.kitchenGUI.setItem(10, quickItem(Material.MELON, Methods.cStr("&2Melon"), 1, Methods.cStr("&6Restores &f+2 hunger &6after drinking."), "", Methods.cStr("&f$500 &7(1x) &8| &f$32,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.kitchenGUI.setItem(11, quickItem(Material.SWEET_BERRIES, Methods.cStr("&cBerries"), 1, Methods.cStr("&6Restores &f+7 saturation &6after drinking."), "", Methods.cStr("&f$1,000 &7(1x) &8| &f$64,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.kitchenGUI.setItem(12, quickItem(Material.SUGAR, Methods.cStr("&fSugar"), 1, Methods.cStr("&6Gives &fspeed 1 for 20 seconds &6after drinking."), "", Methods.cStr("&f$2,000 &7(1x) &8| &f$128,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
        Data.kitchenGUI.setItem(13, quickItem(Material.HONEY_BOTTLE, Methods.cStr("&eIrn Bru"), 1, Methods.cStr("&6Gives &fstrength 1 for 20 seconds &6after drinking."), "", Methods.cStr("&f$4,000 &7(1x) &8| &f$256,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));

        //Tool Menu
        for (int i = 0; i < 3; i++) {
            Data.toolGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        }
        Data.toolGUI.setItem(9, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        Data.toolGUI.setItem(11, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        for (int i = 18; i < 21; i++) {
            Data.toolGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        }
        Data.toolGUI.setItem(13, quickItem(Material.ENCHANTED_BOOK, Methods.cStr("&eEnchantments"), 1, Methods.cStr("&6Enchantments are tiered, usually simple but effective upgrades."), Methods.cStr("&6They don't need much strategising and are straight forward.")));
        Data.toolGUI.setItem(14, quickItem(Material.FIREWORK_ROCKET, Methods.cStr("&eUpgrades"), 1, Methods.cStr("&6Upgrades are complex additions that can be turned off or on."), Methods.cStr("&6Experiment to find a combination of upgrades that best fit your play-style.")));
        Data.toolGUI.setItem(15, quickItem(Material.COMPARATOR, Methods.cStr("&eConfiguration"), 1, Methods.cStr("&6Here you can configure your tool and its upgrades."), Methods.cStr("&6This is useful for maximizing efficiency, profits and deadliness.")));

        //Enchantment Menu
        for (int i = 0; i < 3; i++) {
            Data.enchantmentGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        }
        Data.enchantmentGUI.setItem(9, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        Data.enchantmentGUI.setItem(11, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        for (int i = 18; i < 21; i++) {
            Data.enchantmentGUI.setItem(i, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
        }
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
        inventory.setContents(inventory.getContents());
        if (inventory.equals(Data.foodShopGUI)) {
            if (EconomyManager.getLevel(player.getUniqueId()) < 25) {
                inventory.setItem(13, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
                inventory.setItem(16, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
            }
            if (EconomyManager.getLevel(player.getUniqueId()) < 50) {
                inventory.setItem(14, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
            }
            player.openInventory(inventory);
        }
        if (inventory.equals(Data.kitchenGUI)) {
            if (EconomyManager.getLevel(player.getUniqueId()) < 50) {
                inventory.setItem(12, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
                inventory.setItem(13, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
            }
            player.openInventory(inventory);
        }
        if (inventory.equals(Data.toolGUI)) {
            inventory.setItem(10, player.getInventory().getItemInMainHand());
            if (EconomyManager.getLevel(player.getUniqueId()) < 15) {
                inventory.setItem(14, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 15&c.")));
            }
            if (EconomyManager.getLevel(player.getUniqueId()) < 75) {
                inventory.setItem(15, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 75&c.")));
            }
            player.openInventory(inventory);
        }
        if (inventory.equals(Data.enchantmentGUI)) {
            ItemStack item = player.getInventory().getItemInMainHand();
            inventory.setItem(10, item);
            if (item.getType().toString().contains("PICKAXE")) {
                ItemManager.setGUIEnchant(item, inventory, 3, Methods.cStr("&eEfficiency"), Enchantment.DIG_SPEED, (byte) 0, Methods.cStr("&6Increases mining speed."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 5) inventory.setItem(4, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 5&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 4, Methods.cStr("&eSilk Touch"), Enchantment.SILK_TOUCH, (byte) 1, Methods.cStr("&6Diamond and emerald rewards are multiplied"), Methods.cStr("&6by 0.5 for every level of this enchantment."), item.getEnchantmentLevel(Enchantment.SILK_TOUCH) == ItemManager.getEnchantmentMaxLevel(Enchantment.SILK_TOUCH) ? Methods.cStr("&6Multiplier&8: &f" + (0.5 * item.getEnchantmentLevel(Enchantment.SILK_TOUCH)) + "x") : Methods.cStr("&6Multiplier&8: &7" + (0.5 * item.getEnchantmentLevel(Enchantment.SILK_TOUCH)) + "x &f→ &e" + (0.5 * (item.getEnchantmentLevel(Enchantment.SILK_TOUCH) + 1)) + "x"));
                if (EconomyManager.getLevel(player.getUniqueId()) < 10) inventory.setItem(5, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 10&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 5, Methods.cStr("&eFortune"), Enchantment.LOOT_BONUS_BLOCKS, (byte) 2, Methods.cStr("&6Has a &f5% chance &6per level to double rewards."), item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) == ItemManager.getEnchantmentMaxLevel(Enchantment.LOOT_BONUS_BLOCKS) ? Methods.cStr("&6Chance&8: &f" + item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 5 + "%") : Methods.cStr("&6Chance&8: &7" + item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 5 + "% &f→ &e" + (item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1) * 5 + "%"));
            }
            if (item.getType().toString().contains("SWORD")) {
                ItemManager.setGUIEnchant(item, inventory, 3, Methods.cStr("&eSharpness"), Enchantment.DAMAGE_ALL, (byte) 3, Methods.cStr("&6Adds 0.5 attack damage per level + 0.5."));
                ItemManager.setGUIEnchant(item, inventory, 4, Methods.cStr("&eKnockback"), Enchantment.KNOCKBACK, (byte) 4, Methods.cStr("&6Enemies are knocked back when hit."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 15) inventory.setItem(5, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 15&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 5, Methods.cStr("&eFire Aspect"), Enchantment.FIRE_ASPECT, (byte) 5, Methods.cStr("&6Enemies receive 4 seconds"), Methods.cStr("&6of fire damage when hit per level."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 25) inventory.setItem(6, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 6, Methods.cStr("&eSweeping Edge"), Enchantment.SWEEPING_EDGE, (byte) 6, Methods.cStr("&6Increased attack damage when standing still."));
            }
            if (item.getType().toString().contains("HELMET") | item.getType().toString().contains("CHESTPLATE") | item.getType().toString().contains("LEGGINGS") | item.getType().toString().contains("BOOTS")) {
                ItemManager.setGUIEnchant(item, inventory, 3, Methods.cStr("&eProtection"), Enchantment.PROTECTION_ENVIRONMENTAL, (byte) 7, Methods.cStr("&6Reduces damage taken from most sources."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 15) inventory.setItem(4, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 15&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 4, Methods.cStr("&eFire Protection"), Enchantment.PROTECTION_FIRE, (byte) 8, Methods.cStr("&6Reduces damage taken from fire."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 20) inventory.setItem(5, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 20&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 5, Methods.cStr("&eProjectile Protection"), Enchantment.PROTECTION_PROJECTILE, (byte) 9, Methods.cStr("&6Reduces damage taken from arrows and fireworks."), Methods.cStr("&6of fire damage when hit per level."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 30) inventory.setItem(6, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 30&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 6, Methods.cStr("&eBlast Protection"), Enchantment.PROTECTION_EXPLOSIONS, (byte) 10, Methods.cStr("&6Reduces damage taken from explosions."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 45) inventory.setItem(7, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 45&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 7, Methods.cStr("&eThorns"), Enchantment.THORNS, (byte) 11, Methods.cStr("&6Has a 15% chance per level to"), Methods.cStr("&6reflect 1-4 damage to the attacker."));
            }
            player.openInventory(inventory);
        }
    }
}