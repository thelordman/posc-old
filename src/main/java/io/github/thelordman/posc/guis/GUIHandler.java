package io.github.thelordman.posc.guis;

import io.github.thelordman.posc.economy.EconomyManager;
import io.github.thelordman.posc.food.FoodItem;
import io.github.thelordman.posc.items.ItemManager;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUIHandler {
    private static final ItemStack backGround = quickItem(Material.WHITE_STAINED_GLASS_PANE, Methods.cStr("&r"), 1, "");

    public static ItemStack quickItem(final Material material, final String name, final int amount, final String... lore) {
        final ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(List.of(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack sellItem(final ItemStack item, final double price) {
        ItemManager.addLore(item, "", "&f$" + Methods.rStr(price) + " &7(1x) &8| &f$" + Methods.rStr(price * 64) + " &7(64x)", "",
                "&7Left click to buy one", "&7Right click to buy stack");
        return item;
    }

    public enum GUIType {
        FOOD_SHOP, KITCHEN, SHOP, BLOCK_SHOP, TOOL_MENU, ENCHANTMENT_MENU
    }

    public static void openGUI(GUIType type, Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();

        GUI gui = new GUI(
                Methods.rfStr(type.name().toLowerCase().split("_")[0]) + " " + Methods.rfStr(type.name().toLowerCase().split("_")[1]),
                type == GUIType.BLOCK_SHOP ? 45 : 27);

        switch (type) {
            case BLOCK_SHOP -> {
                for (int i = 0; i < 45; i++) {
                    switch (i) {
                        case 10, 19, 28 -> i += 6;
                        default -> gui.putItem(i, backGround);
                    }
                }
            }
            case TOOL_MENU, ENCHANTMENT_MENU -> {
                for (int i = 0; i < 3; i++) {
                    gui.putItem(i, backGround);
                }
                gui.putItem(9, backGround);
                gui.putItem(11, backGround);
                for (int i = 18; i < 21; i++) {
                    gui.putItem(i, backGround);
                }
            }
            default -> {
                for (int i = 0; i < 27; i++) {
                    if (i == 10) i = 17;
                    gui.putItem(i, backGround);
                }
            }
        }

        switch (type) {
            case FOOD_SHOP -> {
                gui.putItem(10, sellItem(FoodItem.FRESH_BREAD, 10d));
                gui.putItem(11, sellItem(FoodItem.HOT_WINGS, 50d));
                gui.putItem(12, sellItem(FoodItem.WELL_DONE_STEAK, 100d));
                gui.putItem(13, sellItem(FoodItem.BEER, 1000d));
                gui.putItem(14, sellItem(FoodItem.THICK_STEW, 5000d));
                gui.putItem(16, quickItem(Material.CAULDRON, Methods.cStr("&2Kitchen"), 1, Methods.cStr("&6Enter the kitchen menu where you can buy ingredients for Beer.")));
                if (EconomyManager.getLevel(player.getUniqueId()) < 25) {
                    gui.putItem(13, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
                    gui.putItem(16, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
                }
                if (EconomyManager.getLevel(player.getUniqueId()) < 50) {
                    gui.putItem(14, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
                }
            }
            case KITCHEN -> {
                gui.putItem(10, sellItem(FoodItem.MELON, 500d));
                gui.putItem(11, sellItem(FoodItem.BERRIES, 1000d));
                gui.putItem(12, sellItem(FoodItem.SUGAR, 2000d));
                gui.putItem(13, sellItem(FoodItem.IRN_BRU, 4000d));
                if (EconomyManager.getLevel(player.getUniqueId()) < 50) {
                    gui.putItem(12, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
                    gui.putItem(13, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 50&c.")));
                }
            }
            case SHOP -> {
                gui.putItem(10, quickItem(Material.GRASS_BLOCK, Methods.cStr("&2Block Shop"), 1, Methods.cStr("&6Buy building blocks that you can place.")));
            }
            case BLOCK_SHOP -> {
                Material[] materials = {
                        Material.GRASS_BLOCK, Material.DIRT, Material.LADDER, Material.COBWEB, Material.OAK_LEAVES, Material.GRAVEL, Material.SAND,
                        Material.STONE, Material.COBBLESTONE, Material.BLACKSTONE, Material.DEEPSLATE, Material.GLASS, Material.MELON, Material.PUMPKIN,
                        Material.GLOWSTONE, Material.SEA_LANTERN, Material.OAK_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG, Material.QUARTZ_BLOCK, Material.OBSIDIAN
                };
                int ii = 0;
                for (int i = 10; i < 35; i++) {
                    if (gui.getItem(i) != null) continue;
                    Material material = materials[ii];
                    double cost = material == Material.OBSIDIAN | material == Material.COBWEB ? 1000 : 100;
                    gui.putItem(i, quickItem(material, ChatColor.WHITE + Methods.getMaterialName(material), 1, Methods.cStr("&f$" + Methods.rStr(cost) + " &7(1x) &8| &f$" + Methods.rStr(cost * 64) + " &7(64x)")));
                    ii++;
                }
            }
            case TOOL_MENU -> {
                gui.putItem(13, quickItem(Material.ENCHANTED_BOOK, Methods.cStr("&eEnchantments"), 1, Methods.cStr("&6Enchantments are tiered, usually simple but effective upgrades."), Methods.cStr("&6They don't need much strategising and are straight forward.")));
                gui.putItem(15, quickItem(Material.BARRIER, Methods.cStr("&cComing Soon"), 1, ""));
                gui.putItem(16, quickItem(Material.BARRIER, Methods.cStr("&cComing Soon"), 1, ""));

                Material material = switch (item.getType()) {
                    case IRON_PICKAXE -> Material.DIAMOND_PICKAXE;
                    case DIAMOND_PICKAXE -> Material.NETHERITE_PICKAXE;

                    case IRON_SWORD -> Material.DIAMOND_SWORD;
                    case DIAMOND_SWORD -> Material.NETHERITE_SWORD;
                    case NETHERITE_SWORD -> Material.GOLDEN_SWORD;

                    case IRON_HELMET -> Material.DIAMOND_HELMET;
                    case DIAMOND_HELMET -> Material.NETHERITE_HELMET;
                    case NETHERITE_HELMET -> Material.GOLDEN_HELMET;

                    case IRON_CHESTPLATE -> Material.DIAMOND_CHESTPLATE;
                    case DIAMOND_CHESTPLATE -> Material.NETHERITE_CHESTPLATE;
                    case NETHERITE_CHESTPLATE -> Material.GOLDEN_CHESTPLATE;

                    case IRON_LEGGINGS -> Material.DIAMOND_LEGGINGS;
                    case DIAMOND_LEGGINGS -> Material.NETHERITE_LEGGINGS;
                    case NETHERITE_LEGGINGS -> Material.GOLDEN_LEGGINGS;

                    case IRON_BOOTS -> Material.DIAMOND_BOOTS;
                    case DIAMOND_BOOTS -> Material.NETHERITE_BOOTS;
                    case NETHERITE_BOOTS -> Material.GOLDEN_BOOTS;

                    default -> item.getType();
                };
                gui.putItem(14, quickItem(material, Methods.cStr("&eUpgrade Material"), 1,
                        Methods.cStr("&6Upgrade the material of your tool to increase its base stats."),
                        "", item.getType().name().split("_")[0].equals("GOLDEN") | item.getType() == Material.NETHERITE_PICKAXE
                                ? Methods.cStr("&6&lMAX LEVEL")
                                : Methods.cStr("&eCost&8: &f$" + Methods.rStr(ItemManager.getMaterialPrice(material)))));
                gui.putItem(10, player.getInventory().getItemInMainHand());
                if (EconomyManager.getLevel(player.getUniqueId()) < 25) {
                    gui.putItem(14, quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel 25&c.")));
                }
            }
            case ENCHANTMENT_MENU -> {
                int level = EconomyManager.getLevel(player.getUniqueId());
                gui.putItem(10, item);
                if (item.getType().toString().contains("PICKAXE")) {
                    gui.putItem(3, ItemManager.GUIEnchant(item, Methods.cStr("&eEfficiency"), Enchantment.DIG_SPEED, (byte) 0, level, 0, Methods.cStr("&6Increases mining speed.")));
                    gui.putItem(4, ItemManager.GUIEnchant(item, Methods.cStr("&eSilk Touch"), Enchantment.SILK_TOUCH, (byte) 1, level, 2, Methods.cStr("&6Diamond and emerald rewards are multiplied"), Methods.cStr("&6by +0.5 for every level of this enchantment."), item.getEnchantmentLevel(Enchantment.SILK_TOUCH) == ItemManager.getEnchantmentMaxLevel(Enchantment.SILK_TOUCH) ? Methods.cStr("&6Multiplier&8: &f" + (0.5 * item.getEnchantmentLevel(Enchantment.SILK_TOUCH) + 1) + "x") : Methods.cStr("&6Multiplier&8: &7" + (0.5 * item.getEnchantmentLevel(Enchantment.SILK_TOUCH) + 1) + "x &f→ &e" + (0.5 * (item.getEnchantmentLevel(Enchantment.SILK_TOUCH) + 1) + 1) + "x")));
                    gui.putItem(5, ItemManager.GUIEnchant(item, Methods.cStr("&eFortune"), Enchantment.LOOT_BONUS_BLOCKS, (byte) 2, level, 5, Methods.cStr("&6Has a &f5% chance &6per level to double rewards."), item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) == ItemManager.getEnchantmentMaxLevel(Enchantment.LOOT_BONUS_BLOCKS) ? Methods.cStr("&6Chance&8: &f" + item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 5 + "%") : Methods.cStr("&6Chance&8: &7" + item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 5 + "% &f→ &e" + (item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1) * 5 + "%")));

                    gui.putItem(6, ItemManager.GUICE(item, ItemManager.pickaxeEnchantments[0], Material.GOLD_ORE, level, 0, Methods.cStr("&6Has a &f2% chance &6per level to mine all blocks"), Methods.cStr("&6of the same type on a radius of 5."), Methods.cStr("&6Only works on ores."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[0]) ? Methods.cStr("&6Chance&8: &f" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0]) * 2 + "%") : Methods.cStr("&6Chance&8: &7" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0]) * 2 + "% &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[0]) + 1) * 2 + "%")));
                    gui.putItem(7, ItemManager.GUICE(item, ItemManager.pickaxeEnchantments[1], Material.TNT, level, 5, Methods.cStr("&6Has a &f5% chance &6per level to blow up"), Methods.cStr("&6all blocks on a radius of 3."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[1]) ? Methods.cStr("&6Chance&8: &f" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1]) * 5 + "%") : Methods.cStr("&6Chance&8: &7" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1]) * 5 + "% &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[1]) + 1) * 5 + "%")));
                    gui.putItem(8, ItemManager.GUICE(item, ItemManager.pickaxeEnchantments[2], Material.GOLDEN_BOOTS, level, 10, Methods.cStr("&6Gives the speed effect based on the"), Methods.cStr("&6enchantment's level when you mine."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[2]) ? Methods.cStr("&6Effect Level&8: &f" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2])) : Methods.cStr("&6Effect Level&8: &7" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2]) + " &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[2]) + 1))));
                    gui.putItem(12, ItemManager.GUICE(item, ItemManager.pickaxeEnchantments[3], Material.GOLDEN_PICKAXE, level, 15, Methods.cStr("&6Gives haste while held"), Methods.cStr("&6based on the level."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[3]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[3]) ? Methods.cStr("&6Effect Level&8: &f" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[3])) : Methods.cStr("&6Effect Level&8: &7" + ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[3]) + " &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[3]) + 1))));
                    gui.putItem(13, ItemManager.GUICE(item, ItemManager.pickaxeEnchantments[4], Material.EXPERIENCE_BOTTLE, level, 20, Methods.cStr("&6Multiplies your initial experience gain"), Methods.cStr("&6by the level of this enchantment."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[4]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[4]) ? Methods.cStr("&6Multiplier&8: &f" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[4]) + 1) + "x") : Methods.cStr("&6Multiplier&8: &7" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[4]) + 1) + "x &f→ &e" + (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[4]) + 2) + "x")));
                    gui.putItem(14, ItemManager.GUICE(item, ItemManager.pickaxeEnchantments[5], Material.GOLDEN_AXE, level, 30, Methods.cStr("&6Has a &f0.5% &6chance per level"), Methods.cStr("&6to mine a large cylinder."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[5]) ? Methods.cStr("&6Chance&8: &f" + 0.5 * ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5]) + "%") : Methods.cStr("&6Chance&8: &7" + 0.5 * ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5]) + "% &f→ &e" + 0.5 * (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[5]) + 1) + "%")));
                    gui.putItem(15, ItemManager.GUICE(item, ItemManager.pickaxeEnchantments[6], Material.LIGHTNING_ROD, level, 35, Methods.cStr("&6Has a &f5% &6chance per level to mine"), Methods.cStr("&6all blocks above and below you"), Methods.cStr("&6with double the xp and money."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[6]) ? Methods.cStr("&6Chance&8: &f" + 5 * ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6]) + "%") : Methods.cStr("&6Chance&8: &7" + 5 * ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6]) + "% &f→ &e" + 5 * (ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[6]) + 1) + "%")));
                    gui.putItem(16, ItemManager.GUICE(item, ItemManager.pickaxeEnchantments[7], Material.TARGET, level, 40, Methods.cStr("&6Gives a +0.1x multiplier per level for every digit"), Methods.cStr("&6on the number of blocks mined by this pickaxe"), Methods.cStr("&6while this enchantment was on it."), ItemManager.getCELevel(item, ItemManager.pickaxeEnchantments[7]) == ItemManager.getCEMaxLevel(ItemManager.pickaxeEnchantments[7]) ? Methods.cStr("&6Multiplier&8: &f" + Methods.rStr(ItemManager.getCounterMulti(item) + 1) + "x") : Methods.cStr("&6Multiplier&8: &7" + Methods.rStr(ItemManager.getCounterMulti(item) + 1) + "x &f→ &e" + Methods.rStr(ItemManager.getCounterMulti(item) + 1.1) + "x")));
                }
                if (item.getType().toString().contains("SWORD")) {
                    gui.putItem(3, ItemManager.GUIEnchant(item, Methods.cStr("&eSharpness"), Enchantment.DAMAGE_ALL, (byte) 3, level, 0, Methods.cStr("&6Adds 0.5 attack damage per level + 0.5.")));
                    gui.putItem(4, ItemManager.GUIEnchant(item, Methods.cStr("&eKnockback"), Enchantment.KNOCKBACK, (byte) 4, level, 0, Methods.cStr("&6Enemies are knocked back when hit.")));
                    gui.putItem(5, ItemManager.GUIEnchant(item, Methods.cStr("&eFire Aspect"), Enchantment.FIRE_ASPECT, (byte) 5, level, 10, Methods.cStr("&6Enemies receive 4 seconds"), Methods.cStr("&6of fire damage when hit per level.")));
                    gui.putItem(6, ItemManager.GUIEnchant(item, Methods.cStr("&eSweeping Edge"), Enchantment.SWEEPING_EDGE, (byte) 6, level, 25, Methods.cStr("&6Increased attack damage when standing still.")));

                    gui.putItem(7, ItemManager.GUICE(item, ItemManager.swordEnchantments[0], Material.POISONOUS_POTATO, level, 0, Methods.cStr("&f2% chance &6per level to inflict"), Methods.cStr("&6poison to victim when hit."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[0]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[0]) ? Methods.cStr("&6Chance&8: &f" + 2 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[0]) + "%") : Methods.cStr("&6Chance&8: &7" + 2 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[0]) + "% &f→ &e" + 2 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[0]) + 1) + "%")));
                    gui.putItem(8, ItemManager.GUICE(item, ItemManager.swordEnchantments[1], Material.PLAYER_HEAD, level, 10, Methods.cStr("&6Steal &f20% &6of your"), Methods.cStr("&6victim's balance on kill,"), Methods.cStr("&6if the victim's level is bigger.")));
                    gui.putItem(12, ItemManager.GUICE(item, ItemManager.swordEnchantments[2], Material.SHIELD, level, 15, Methods.cStr("&6Has a &f5% chance &6per level to"), Methods.cStr("&6reduce the damage dealt to you by a third"), Methods.cStr("&6while holding an item with this enchantment."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[2]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[2]) ? Methods.cStr("&6Chance&8: &f" + 5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[2]) + "%") : Methods.cStr("&6Chance&8: &7" + 5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[2]) + "% &f→ &e" + 5 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[2]) + 1) + "%")));
                    gui.putItem(13, ItemManager.GUICE(item, ItemManager.swordEnchantments[3], Material.ENDER_EYE, level, 20, Methods.cStr("&6Has a &f2% chance &6per level to"), Methods.cStr("&6give the blindness effect"), Methods.cStr("&6to victim when hit."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[3]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[3]) ? Methods.cStr("&6Chance&8: &f" + 2 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[3]) + "%") : Methods.cStr("&6Chance&8: &7" + 2 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[3]) + "% &f→ &e" + 2 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[3]) + 1) + "%")));
                    gui.putItem(14, ItemManager.GUICE(item, ItemManager.swordEnchantments[4], Material.BREAD, level, 25, Methods.cStr("&6Restores 0.5 food and 0.5 saturation"), Methods.cStr("&6on hit per level."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[4]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[4]) ? Methods.cStr("&6Restore&8: &f" + Methods.rStr(0.5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[4])) + " food and saturation") : Methods.cStr("&6Restore&8: &7" + Methods.rStr(0.5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[4])) + " &f→ &e" + Methods.rStr(0.5 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[4]) + 1)))));
                    gui.putItem(15, ItemManager.GUICE(item, ItemManager.swordEnchantments[5], Material.REDSTONE, level, 30, Methods.cStr("&6Deals &f+1% damage &6per level"), Methods.cStr("&6for every hit you've dealt"), Methods.cStr("&6to the enemy."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[5]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[5]) ? Methods.cStr("&6Bonus Damage&8: &f+" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[5]) + "%") : Methods.cStr("&6Bonus Damage&8: &7+" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[5]) + "% &f→ &e+" + (ItemManager.getCELevel(item, ItemManager.swordEnchantments[5]) + 1) + "%")));
                    gui.putItem(16, ItemManager.GUICE(item, ItemManager.swordEnchantments[6], Material.WITHER_ROSE, level, 35, Methods.cStr("&f5% chance &6per level to"), Methods.cStr("&6steal &f0.5 health &6per level"), Methods.cStr("&6from the enemy."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[6]) ? Methods.cStr("&6Chance&8: &f" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) * 5 + "%") : Methods.cStr("&6Chance&8: &7" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) * 5 + "% &f→ &e" + (ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) + 1) * 5 + "%"), ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[6]) ? Methods.cStr("&6Steal&8: &f" + Methods.rStr(0.5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[6])) + " health") : Methods.cStr("&6Steal&8: &7" + Methods.rStr(0.5 * ItemManager.getCELevel(item, ItemManager.swordEnchantments[6])) + " &f→ &e" + Methods.rStr(0.5 * (ItemManager.getCELevel(item, ItemManager.swordEnchantments[6]) + 1)))));
                    gui.putItem(17, ItemManager.GUICE(item, ItemManager.swordEnchantments[7], Material.MILK_BUCKET, level, 40, Methods.cStr("&6Has a &f2% chance &6per level to"), Methods.cStr("&6remove all negative effects"), Methods.cStr("&6and extinguish fire."), ItemManager.getCELevel(item, ItemManager.swordEnchantments[7]) == ItemManager.getCEMaxLevel(ItemManager.swordEnchantments[7]) ? Methods.cStr("&6Chance&8: &f" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[7]) * 2 + "%") : Methods.cStr("&6Chance&8: &7" + ItemManager.getCELevel(item, ItemManager.swordEnchantments[7]) * 2 + "% &f→ &e" + (ItemManager.getCELevel(item, ItemManager.swordEnchantments[7]) + 1) * 2 + "%")));
                }
                if (item.getType().toString().contains("HELMET") | item.getType().toString().contains("CHESTPLATE") | item.getType().toString().contains("LEGGINGS") | item.getType().toString().contains("BOOTS")) {
                    gui.putItem(3, ItemManager.GUIEnchant(item, Methods.cStr("&eProtection"), Enchantment.PROTECTION_ENVIRONMENTAL, (byte) 7, level, 0, Methods.cStr("&6Reduces damage taken from most sources.")));
                    gui.putItem(4, ItemManager.GUIEnchant(item, Methods.cStr("&eFire Protection"), Enchantment.PROTECTION_FIRE, (byte) 8, level, 7, Methods.cStr("&6Reduces damage taken from fire.")));
                    gui.putItem(5, ItemManager.GUIEnchant(item, Methods.cStr("&eProjectile Protection"), Enchantment.PROTECTION_PROJECTILE, (byte) 9, level, 15, Methods.cStr("&6Reduces damage taken from arrows and fireworks."), Methods.cStr("&6of fire damage when hit per level.")));
                    gui.putItem(6, ItemManager.GUIEnchant(item, Methods.cStr("&eBlast Protection"), Enchantment.PROTECTION_EXPLOSIONS, (byte) 10, level, 25, Methods.cStr("&6Reduces damage taken from explosions.")));
                    gui.putItem(7, ItemManager.GUIEnchant(item, Methods.cStr("&eThorns"), Enchantment.THORNS, (byte) 11, level, 35, Methods.cStr("&6Has a 15% chance per level to"), Methods.cStr("&6reflect 1-4 damage to the attacker.")));
                }
            }
        }
        gui.open(player);
    }
}