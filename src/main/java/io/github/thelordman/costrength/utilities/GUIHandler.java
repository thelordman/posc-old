package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUIHandler {
    public static void registerInventories() {
        //Food Shop and Kitchen
        for (int i = 0; i < 2; i++) {
            for (int i1 = 0; i1 < 10; i1++) {
                Data.GUIs[i].setItem(i1, quickItem(Material.WHITE_STAINED_GLASS_PANE, "", 1, ""));
            }
            for (int i1 = 17; i1 < 27; i1++) {
                Data.GUIs[i].setItem(i1, quickItem(Material.WHITE_STAINED_GLASS_PANE, "", 1, ""));
            }
        }

        //Tool Menu and Enchantment Menu
        for (int i = 2; i < 4; i++) {
            for (int i1 = 0; i1 < 3; i1++) {
                Data.GUIs[i].setItem(i1, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
            }
            Data.GUIs[i].setItem(9, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
            Data.GUIs[i].setItem(11, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
            for (int i1 = 18; i1 < 21; i1++) {
                Data.GUIs[i].setItem(i1, quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, ""));
            }
        }

        //Tool Menu
        Data.GUIs[2].setItem(13, quickItem(Material.ENCHANTED_BOOK, Methods.cStr("&eEnchantments"), 1, Methods.cStr("&6Enchantments are tiered, usually simple but effective upgrades."), Methods.cStr("&6They don't need much strategising and are straight forward.")));
        Data.GUIs[2].setItem(14, quickItem(Material.FIREWORK_ROCKET, Methods.cStr("&eUpgrades"), 1, Methods.cStr("&6Upgrades are complex additions that can be turned off or on."), Methods.cStr("&6Experiment to find a combination of upgrades that best fit your play-style.")));
        Data.GUIs[2].setItem(15, quickItem(Material.COMPARATOR, Methods.cStr("&eConfiguration"), 1, Methods.cStr("&6Here you can configure your tool and its upgrades."), Methods.cStr("&6This is useful for maximizing efficiency, profits and deadliness.")));
    }

    public static ItemStack quickItem(final Material material, final String name, final int amount, final String... lore) {
        final ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(List.of(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static void openGUI(Inventory base, Player player) {
        if (base.equals(Data.GUIs[0])) {
            Inventory inventory = Bukkit.createInventory(player, base.getSize(), "Food Shop");
            inventory.setContents(base.getContents());
            inventory.setItem(10, quickItem(Material.BREAD, Methods.cStr("&fFresh Bread"), 1, Methods.cStr("&6Restores &f2.5 hunger &6and &f3 saturation&6."), "", Methods.cStr("&f$10 &7(1x) &8| &f$640 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
            inventory.setItem(11, quickItem(Material.COOKED_CHICKEN, Methods.cStr("&cHot Wings"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f4.5 saturation&6."), Methods.cStr("&6Has a &f10% chance &6of giving &fHaste +1&6."), "", Methods.cStr("&f$50 &7(1x) &8| &f$3,200 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
            inventory.setItem(12, quickItem(Material.COOKED_BEEF, Methods.cStr("&4Well Done Steak"), 1, Methods.cStr("&6Restores &f4 hunger &6and &f6.5 saturation&6."), "", Methods.cStr("&f$100 &7(1x) &8| &f$6,400 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
            inventory.setItem(13, quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&f$1,000 &7(1x) &8| &f$64,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
            inventory.setItem(14, quickItem(Material.RABBIT_STEW, Methods.cStr("&3Thick Stew"), 1, Methods.cStr("&6Restores &f20 hunger &6and &f20 saturation&6."), "", Methods.cStr("&f$5,000 &7(1x) &8| &f$320,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
            inventory.setItem(16, quickItem(Material.CAULDRON, Methods.cStr("&2Kitchen"), 1, Methods.cStr("&6Enter the kitchen menu where you can buy ingredients for Beer.")));
            if (EconomyManager.getLevel(player.getUniqueId()) < 25) {
                inventory.setItem(13, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
                inventory.setItem(16, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
            }
            if (EconomyManager.getLevel(player.getUniqueId()) < 50) {
                inventory.setItem(14, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
            }
            player.openInventory(inventory);
            return;
        }
        if (base.equals(Data.GUIs[1])) {
            Inventory inventory = Bukkit.createInventory(player, base.getSize(), "Kitchen");
            inventory.setContents(base.getContents());
            inventory.setItem(10, quickItem(Material.MELON, Methods.cStr("&2Melon"), 1, Methods.cStr("&6Restores &f+2 hunger &6after drinking."), "", Methods.cStr("&f$500 &7(1x) &8| &f$32,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
            inventory.setItem(11, quickItem(Material.SWEET_BERRIES, Methods.cStr("&cBerries"), 1, Methods.cStr("&6Restores &f+7 saturation &6after drinking."), "", Methods.cStr("&f$1,000 &7(1x) &8| &f$64,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
            inventory.setItem(12, quickItem(Material.SUGAR, Methods.cStr("&fSugar"), 1, Methods.cStr("&6Gives &fspeed 1 for 20 seconds &6after drinking."), "", Methods.cStr("&f$2,000 &7(1x) &8| &f$128,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
            inventory.setItem(13, quickItem(Material.HONEY_BOTTLE, Methods.cStr("&eIrn Bru"), 1, Methods.cStr("&6Gives &fstrength 1 for 20 seconds &6after drinking."), "", Methods.cStr("&f$4,000 &7(1x) &8| &f$256,000 &7(64x)"), "", "Left click to buy one", "Right click to buy stack"));
            if (EconomyManager.getLevel(player.getUniqueId()) < 50) {
                inventory.setItem(12, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
                inventory.setItem(13, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
            }
            player.openInventory(inventory);
            return;
        }
        if (base.equals(Data.GUIs[2])) {
            Inventory inventory = Bukkit.createInventory(player, base.getSize(), "Tool Menu");
            inventory.setContents(base.getContents());
            Data.GUIs[2].setItem(13, quickItem(Material.ENCHANTED_BOOK, Methods.cStr("&eEnchantments"), 1, Methods.cStr("&6Enchantments are tiered, usually simple but effective upgrades."), Methods.cStr("&6They don't need much strategising and are straight forward.")));
            Data.GUIs[2].setItem(14, quickItem(Material.FIREWORK_ROCKET, Methods.cStr("&eUpgrades"), 1, Methods.cStr("&6Upgrades are complex additions that can be turned off or on."), Methods.cStr("&6Experiment to find a combination of upgrades that best fit your play-style.")));
            Data.GUIs[2].setItem(15, quickItem(Material.COMPARATOR, Methods.cStr("&eConfiguration"), 1, Methods.cStr("&6Here you can configure your tool and its upgrades."), Methods.cStr("&6This is useful for maximizing efficiency, profits and deadliness.")));
            inventory.setItem(10, player.getInventory().getItemInMainHand());
            if (EconomyManager.getLevel(player.getUniqueId()) < 15) {
                inventory.setItem(14, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 15&c.")));
            }
            if (EconomyManager.getLevel(player.getUniqueId()) < 75) {
                inventory.setItem(15, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 75&c.")));
            }
            player.openInventory(inventory);
            return;
        }
        if (base.equals(Data.GUIs[3])) {
            int level = EconomyManager.getLevel(player.getUniqueId());
            Inventory inventory = Bukkit.createInventory(player, base.getSize(), "Enchantment Menu");
            inventory.setContents(base.getContents());
            ItemStack item = player.getInventory().getItemInMainHand();
            inventory.setItem(10, item);
            if (item.getType().toString().contains("PICKAXE")) {
                ItemManager.setGUIEnchant(item, inventory, 3, Methods.cStr("&eEfficiency"), Enchantment.DIG_SPEED, (byte) 0, Methods.cStr("&6Increases mining speed."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 2) inventory.setItem(4, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 2&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 4, Methods.cStr("&eSilk Touch"), Enchantment.SILK_TOUCH, (byte) 1, Methods.cStr("&6Diamond and emerald rewards are multiplied"), Methods.cStr("&6by +0.5 for every level of this enchantment."), item.getEnchantmentLevel(Enchantment.SILK_TOUCH) == ItemManager.getEnchantmentMaxLevel(Enchantment.SILK_TOUCH) ? Methods.cStr("&6Multiplier&8: &f" + (0.5 * item.getEnchantmentLevel(Enchantment.SILK_TOUCH) + 1) + "x") : Methods.cStr("&6Multiplier&8: &7" + (0.5 * item.getEnchantmentLevel(Enchantment.SILK_TOUCH) + 1) + "x &f→ &e" + (0.5 * (item.getEnchantmentLevel(Enchantment.SILK_TOUCH) + 1) + 1) + "x"));
                if (EconomyManager.getLevel(player.getUniqueId()) < 5) inventory.setItem(5, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 5&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 5, Methods.cStr("&eFortune"), Enchantment.LOOT_BONUS_BLOCKS, (byte) 2, Methods.cStr("&6Has a &f5% chance &6per level to double rewards."), item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) == ItemManager.getEnchantmentMaxLevel(Enchantment.LOOT_BONUS_BLOCKS) ? Methods.cStr("&6Chance&8: &f" + item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 5 + "%") : Methods.cStr("&6Chance&8: &7" + item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 5 + "% &f→ &e" + (item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1) * 5 + "%"));

                ItemManager.setGUICE(item, inventory, 6, ItemManager.pickaxeEnchantments[0], Material.GOLD_ORE, level, 0, Methods.cStr("&6Has a &f2% chance &6per level to mine all blocks"), Methods.cStr("&6of the same type on a radius of 5."), Methods.cStr("&6Only works on ores."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[0]) ? Methods.cStr("&6Chance&8: &f" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0]) * 2 + "%") : Methods.cStr("&6Chance&8: &7" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0]) * 2 + "% &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0]) + 1) * 2 + "%"));
                ItemManager.setGUICE(item, inventory, 7, ItemManager.pickaxeEnchantments[1], Material.TNT, level, 5, Methods.cStr("&6Has a &f5% chance &6per level to blow up"), Methods.cStr("&6all blocks on a radius of 3."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[1]) ? Methods.cStr("&6Chance&8: &f" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1]) * 5 + "%") : Methods.cStr("&6Chance&8: &7" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1]) * 5 + "% &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1]) + 1) * 5 + "%"));
                ItemManager.setGUICE(item, inventory, 8, ItemManager.pickaxeEnchantments[2], Material.GOLDEN_BOOTS, level, 10, Methods.cStr("&6Gives the speed effect based on the"), Methods.cStr("&6enchantment's level when you mine."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[2]) ? Methods.cStr("&6Effect Level&8: &f" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2])) : Methods.cStr("&6Effect Level&8: &7" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2]) + " &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2]) + 1)));
                ItemManager.setGUICE(item, inventory, 12, ItemManager.pickaxeEnchantments[3], Material.GOLDEN_PICKAXE, level, 15, Methods.cStr("&6Gives haste while held"), Methods.cStr("&6based on the level."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[3]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[3]) ? Methods.cStr("&6Effect Level&8: &f" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[3])) : Methods.cStr("&6Effect Level&8: &7" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[3]) + " &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[3]) + 1)));
                ItemManager.setGUICE(item, inventory, 13, ItemManager.pickaxeEnchantments[4], Material.EXPERIENCE_BOTTLE, level, 20, Methods.cStr("&6Multiplies your initial experience gain"), Methods.cStr("&6by the level of this enchantment."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[4]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[4]) ? Methods.cStr("&6Multiplier&8: &f" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[4]) + 1) + "x") : Methods.cStr("&6Multiplier&8: &7" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[4]) + 1) + "x &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[4]) + 2) + "x"));
                ItemManager.setGUICE(item, inventory, 14, ItemManager.pickaxeEnchantments[5], Material.GOLDEN_AXE, level, 30, Methods.cStr("&6Has a &f0.5% &6chance per level"), Methods.cStr("&6to mine a large cylinder."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[5]) ? Methods.cStr("&6Chance&8: &f" + 0.5 * ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5]) + "%") : Methods.cStr("&6Chance&8: &7" + 0.5 * ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5]) + "% &f→ &e" + 0.5 * (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5]) + 1) + "%"));
                ItemManager.setGUICE(item, inventory, 15, ItemManager.pickaxeEnchantments[6], Material.LIGHTNING_ROD, level, 35, Methods.cStr("&6Has a &f5% &6chance per level to mine"), Methods.cStr("&6all blocks above and below you"), Methods.cStr("&6with double the xp and money."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[6]) ? Methods.cStr("&6Chance&8: &f" + 5 * ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6]) + "%") : Methods.cStr("&6Chance&8: &7" + 5 * ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6]) + "% &f→ &e" + 5 * (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6]) + 1) + "%"));
                ItemManager.setGUICE(item, inventory, 16, ItemManager.pickaxeEnchantments[7], Material.TARGET, level, 40, Methods.cStr("&6Gives a +0.1x multiplier per level for every digit"), Methods.cStr("&6on the number of blocks mined by this pickaxe"), Methods.cStr("&6while this enchantment was on it."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[7]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[7]) ? Methods.cStr("&6Multiplier&8: &f" + Methods.rStr(ItemManager.getCounterMulti(item) + 1) + "x") : Methods.cStr("&6Multiplier&8: &7" + Methods.rStr(ItemManager.getCounterMulti(item) + 1) + "x &f→ &e" + Methods.rStr(ItemManager.getCounterMulti(item) + 1.1) + "x"));
            }
            if (item.getType().toString().contains("SWORD")) {
                ItemManager.setGUIEnchant(item, inventory, 3, Methods.cStr("&eSharpness"), Enchantment.DAMAGE_ALL, (byte) 3, Methods.cStr("&6Adds 0.5 attack damage per level + 0.5."));
                ItemManager.setGUIEnchant(item, inventory, 4, Methods.cStr("&eKnockback"), Enchantment.KNOCKBACK, (byte) 4, Methods.cStr("&6Enemies are knocked back when hit."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 10) inventory.setItem(5, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 10&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 5, Methods.cStr("&eFire Aspect"), Enchantment.FIRE_ASPECT, (byte) 5, Methods.cStr("&6Enemies receive 4 seconds"), Methods.cStr("&6of fire damage when hit per level."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 25) inventory.setItem(6, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 6, Methods.cStr("&eSweeping Edge"), Enchantment.SWEEPING_EDGE, (byte) 6, Methods.cStr("&6Increased attack damage when standing still."));

                ItemManager.setGUICE(item, inventory, 7, ItemManager.swordEnchantments[0], Material.POISONOUS_POTATO, level, 0, Methods.cStr("&f2% chance &6per level to inflict"), Methods.cStr("&6poison to victim when hit."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[0]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[0]) ? Methods.cStr("&6Chance&8: &f" + 2 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[0]) + "%") : Methods.cStr("&6Chance&8: &7" + 2 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[0]) + "% &f→ &e" + 2 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[0]) + 1) + "%"));
                ItemManager.setGUICE(item, inventory, 8, ItemManager.swordEnchantments[1], Material.PLAYER_HEAD, level, 10, Methods.cStr("&6Steal &f20% &6of your"), Methods.cStr("&6victim's balance on kill,"), Methods.cStr("&6if the victim's level is bigger."));
                ItemManager.setGUICE(item, inventory, 12, ItemManager.swordEnchantments[2], Material.SHIELD, level, 15, Methods.cStr("&6Has a &f5% chance &6per level to"), Methods.cStr("&6reduce the damage dealt to you by a third"), Methods.cStr("&6while holding an item with this enchantment."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[2]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[2]) ? Methods.cStr("&6Chance&8: &f" + 5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[2]) + "%") : Methods.cStr("&6Chance&8: &7" + 5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[2]) + "% &f→ &e" + 5 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[2]) + 1) + "%"));
                ItemManager.setGUICE(item, inventory, 13, ItemManager.swordEnchantments[3], Material.ENDER_EYE, level, 20, Methods.cStr("&6Has a &f2% chance &6per level to"), Methods.cStr("&6give the blindness effect"), Methods.cStr("&6to victim when hit."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[3]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[3]) ? Methods.cStr("&6Chance&8: &f" + 2 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[3]) + "%") : Methods.cStr("&6Chance&8: &7" + 2 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[3]) + "% &f→ &e" + 2 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[3]) + 1) + "%"));
                ItemManager.setGUICE(item, inventory, 14, ItemManager.swordEnchantments[4], Material.BREAD, level, 25, Methods.cStr("&6Restores 0.5 food and 0.5 saturation"), Methods.cStr("&6on hit per level."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[4]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[4]) ? Methods.cStr("&6Restore&8: &f" + Methods.rStr(0.5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[4])) + " food and saturation") : Methods.cStr("&6Restore&8: &7" + Methods.rStr(0.5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[4])) + " &f→ &e" + Methods.rStr(0.5 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[4]) + 1))));
                ItemManager.setGUICE(item, inventory, 15, ItemManager.swordEnchantments[5], Material.REDSTONE, level, 30, Methods.cStr("&6Deals &f+1% damage &6per level"), Methods.cStr("&6for every hit you've dealt"), Methods.cStr("&6to the enemy."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[5]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[5]) ? Methods.cStr("&6Bonus Damage&8: &f+" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[5]) + "%") : Methods.cStr("&6Bonus Damage&8: &7+" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[5]) + "% &f→ &e+" + (ItemManager.getCELevel(item, ItemManager.swordEnchantments[5]) + 1) + "%"));
                ItemManager.setGUICE(item, inventory, 16, ItemManager.swordEnchantments[6], Material.WITHER_ROSE, level, 35, Methods.cStr("&f5% chance &6per level to"), Methods.cStr("&6steal &f0.5 health &6per level"), Methods.cStr("&6from the enemy."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[6]) ? Methods.cStr("&6Chance&8: &f" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) * 5 + "%") : Methods.cStr("&6Chance&8: &7" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) * 5 + "% &f→ &e" + (ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) + 1) * 5 + "%"), ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[6]) ? Methods.cStr("&6Steal&8: &f" + Methods.rStr(0.5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[6])) + " health") : Methods.cStr("&6Steal&8: &7" + Methods.rStr(0.5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[6])) + " &f→ &e" + Methods.rStr(0.5 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) + 1))));
                ItemManager.setGUICE(item, inventory, 17, ItemManager.swordEnchantments[7], Material.MILK_BUCKET, level, 40, Methods.cStr("&6Has a &f2% chance &6per level to"), Methods.cStr("&6remove all negative effects"), Methods.cStr("&6and extinguish fire."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[7]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[7]) ? Methods.cStr("&6Chance&8: &f" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[7]) * 2 + "%") : Methods.cStr("&6Chance&8: &7" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[7]) * 2 + "% &f→ &e" + (ItemManager.getCELevel(item, ItemManager.swordEnchantments[7]) + 1) * 2 + "%"));
            }
            if (item.getType().toString().contains("HELMET") | item.getType().toString().contains("CHESTPLATE") | item.getType().toString().contains("LEGGINGS") | item.getType().toString().contains("BOOTS")) {
                ItemManager.setGUIEnchant(item, inventory, 3, Methods.cStr("&eProtection"), Enchantment.PROTECTION_ENVIRONMENTAL, (byte) 7, Methods.cStr("&6Reduces damage taken from most sources."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 7) inventory.setItem(4, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 7&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 4, Methods.cStr("&eFire Protection"), Enchantment.PROTECTION_FIRE, (byte) 8, Methods.cStr("&6Reduces damage taken from fire."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 15) inventory.setItem(5, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 15&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 5, Methods.cStr("&eProjectile Protection"), Enchantment.PROTECTION_PROJECTILE, (byte) 9, Methods.cStr("&6Reduces damage taken from arrows and fireworks."), Methods.cStr("&6of fire damage when hit per level."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 20) inventory.setItem(6, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 20&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 6, Methods.cStr("&eBlast Protection"), Enchantment.PROTECTION_EXPLOSIONS, (byte) 10, Methods.cStr("&6Reduces damage taken from explosions."));
                if (EconomyManager.getLevel(player.getUniqueId()) < 35) inventory.setItem(7, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 35&c.")));
                else ItemManager.setGUIEnchant(item, inventory, 7, Methods.cStr("&eThorns"), Enchantment.THORNS, (byte) 11, Methods.cStr("&6Has a 15% chance per level to"), Methods.cStr("&6reflect 1-4 damage to the attacker."));
            }
            player.openInventory(inventory);
        }
    }
}