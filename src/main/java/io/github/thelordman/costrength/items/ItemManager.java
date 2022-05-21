package io.github.thelordman.costrength.items;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemManager {
    public static final NamespacedKey[] pickaxeEnchantments = {new NamespacedKey(CoStrength.instance, "pickaxe-vein")};

    public static byte getCELevel(ItemStack item, NamespacedKey key) {
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (container.has(key, PersistentDataType.BYTE)) {
            return container.get(key, PersistentDataType.BYTE);
        }
        return 0;
    }

    public static void setCELevel(ItemStack item, NamespacedKey key, byte level) {
        item.getItemMeta().getPersistentDataContainer().set(key, PersistentDataType.BYTE, level);
        addLore(item, enchantmentNameFromKey(key));
    }

    public static byte getEnchantmentMaxLevel(Enchantment enchantment) {
        return switch (enchantment.getKey().getKey()) {
            case "efficiency" -> 6;
            case "fortune", "sharpness" -> 5;
            case "sweeping", "protection", "fire_protection", "projectile_protection", "blast_protection" -> 4;
            case "silk_touch", "fire_aspect", "thorns" -> 3;
            case "knockback" -> 2;
            default -> 0;
        };
    }


    public static byte getCEMaxLevel(NamespacedKey key) {
        return switch (key.getKey()) {
            case "vein" -> 7;
            default -> 0;
        };
    }

    public static void addLore(ItemStack item, String... strings) {
        List<String> lore = item.getLore() == null ? new ArrayList<>() : item.getLore();
        Collections.addAll(lore, strings);
        item.setLore(lore);
    }

    public static String enchantmentNameFromKey(NamespacedKey key) {
        return switch (key.getKey()) {
            case "pickaxe-vein" -> Methods.cStr("&9Vein");
            default -> "";
        };
    }

    public static double getCEPrice(ItemStack item, NamespacedKey key) {
        switch (key.getKey()) {
            case "pickaxe-vein" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 50000d;
                    case 1 -> 100000d;
                    case 2 -> 175000d;
                    case 3 -> 300000d;
                    case 4 -> 450000d;
                    case 5 -> 750000d;
                    case 6 -> 1000000d;
                    default -> 0d;
                };
            }
        }
        return 0d;
    }

    public static double getEnchantmentPrice(ItemStack item, Enchantment enchantment) {
        switch (enchantment.getKey().getKey()) {
            case "efficiency" -> {
                return switch (item.getEnchantmentLevel(Enchantment.DIG_SPEED)) {
                    case 0 -> 1000d;
                    case 1 -> 2500d;
                    case 2 -> 7500d;
                    case 3 -> 15000d;
                    case 4 -> 25000d;
                    case 5 -> 50000d;
                    default -> 0d;
                };
            }
            case "silk_touch" -> {
                return switch (item.getEnchantmentLevel(Enchantment.SILK_TOUCH)) {
                    case 0 -> 5000d;
                    case 1 -> 10000d;
                    case 2 -> 20000d;
                    default -> 0d;
                };
            }
            case "fortune" -> {
                return switch (item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)) {
                    case 0 -> 12500d;
                    case 1 -> 25000d;
                    case 2 -> 35000d;
                    case 3 -> 50000d;
                    case 4 -> 75000d;
                    default -> 0d;
                };
            }
            case "sharpness" -> {
                return switch (item.getEnchantmentLevel(Enchantment.DAMAGE_ALL)) {
                    case 0 -> 2000d;
                    case 1 -> 5000d;
                    case 2 -> 15000d;
                    case 3 -> 45000d;
                    case 4 -> 100000d;
                    default -> 0d;
                };
            }
            case "knockback" -> {
                return switch (item.getEnchantmentLevel(Enchantment.KNOCKBACK)) {
                    case 0 -> 2500d;
                    case 1 -> 10000d;
                    default -> 0d;
                };
            }
            case "fire_aspect" -> {
                return switch (item.getEnchantmentLevel(Enchantment.FIRE_ASPECT)) {
                    case 0 -> 25000d;
                    case 1 -> 50000d;
                    case 2 -> 100000d;
                    default -> 0d;
                };
            }
            case "sweeping" -> {
                return switch (item.getEnchantmentLevel(Enchantment.SWEEPING_EDGE)) {
                    case 0 -> 50000d;
                    case 1 -> 100000d;
                    case 2 -> 250000d;
                    case 3 -> 500000d;
                    default -> 0d;
                };
            }
            case "protection" -> {
                return switch (item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    case 0 -> 10000d;
                    case 1 -> 25000d;
                    case 2 -> 100000d;
                    case 3 -> 250000d;
                    default -> 0d;
                };
            }
            case "fire_protection" -> {
                return switch (item.getEnchantmentLevel(Enchantment.PROTECTION_FIRE)) {
                    case 0 -> 75000d;
                    case 1 -> 150000d;
                    case 2 -> 500000d;
                    case 3 -> 1000000d;
                    default -> 0d;
                };
            }
            case "projectile_protection" -> {
                return switch (item.getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE)) {
                    case 0 -> 90000d;
                    case 1 -> 125000d;
                    case 2 -> 300000d;
                    case 3 -> 500000d;
                    default -> 0d;
                };
            }
            case "blast_protection" -> {
                return switch (item.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS)) {
                    case 0 -> 95000d;
                    case 1 -> 130000d;
                    case 2 -> 350000d;
                    case 3 -> 600000d;
                    default -> 0d;
                };
            }
            case "thorns" -> {
                return switch (item.getEnchantmentLevel(Enchantment.THORNS)) {
                    case 0 -> 100000d;
                    case 1 -> 150000d;
                    case 2 -> 400000d;
                    default -> 0d;
                };
            }
        }
        return 0d;
    }

    public static void setGUIEnchant(ItemStack item, Inventory inventory, int index, String name, Enchantment enchant, byte key, String... lore) {
        ItemStack GUIItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = GUIItem.getItemMeta();
        meta.setDisplayName(item.getEnchantmentLevel(enchant) == 0
                ? Methods.cStr(name + " &eI")
                : Methods.cStr(name + " &7" + Methods.toRomanNumeral((byte) item.getEnchantmentLevel(enchant)) + " &f→ &e" + Methods.toRomanNumeral((byte) (item.getEnchantmentLevel(enchant) + 1))));
        if (item.getEnchantmentLevel(enchant) == getEnchantmentMaxLevel(enchant)) meta.setDisplayName(Methods.cStr(name + " &6" + Methods.toRomanNumeral((byte)item.getEnchantmentLevel(enchant))));
        meta.setLore(List.of(lore));
        meta.getPersistentDataContainer().set(new NamespacedKey(CoStrength.instance, "gui-item"), PersistentDataType.BYTE, key);
        GUIItem.setItemMeta(meta);
        String addendum = item.getEnchantmentLevel(enchant) >= getEnchantmentMaxLevel(enchant) ? Methods.cStr("&6&lMAX LEVEL") : Methods.cStr("&eCost&8: &f$" + Methods.rStr(getEnchantmentPrice(item, enchant)));
        addLore(GUIItem, "", addendum);
        inventory.setItem(index, GUIItem);
    }

    public static void setEnchant(ItemStack item, Enchantment enchant, byte level) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchant, level, true);
        item.setItemMeta(meta);
    }
}