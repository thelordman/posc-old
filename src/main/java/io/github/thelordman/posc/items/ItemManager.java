package io.github.thelordman.posc.items;

import io.github.thelordman.posc.Posc;
import io.github.thelordman.posc.guis.GUIHandler;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static final NamespacedKey[] pickaxeEnchantments = {new NamespacedKey(Posc.get(), "pickaxe-vein"), new NamespacedKey(Posc.get(), "pickaxe-bomb"), new NamespacedKey(Posc.get(), "pickaxe-speed"), new NamespacedKey(Posc.get(), "pickaxe-haste"), new NamespacedKey(Posc.get(), "pickaxe-experience"), new NamespacedKey(Posc.get(), "pickaxe-jackhammer"), new NamespacedKey(Posc.get(), "pickaxe-drill"), new NamespacedKey(Posc.get(), "pickaxe-counter")};
    public static final NamespacedKey pickaxeCounter = new NamespacedKey(Posc.get(), "counter-pickaxe");

    public static final NamespacedKey[] swordEnchantments = {new NamespacedKey(Posc.get(), "sword-viper"), new NamespacedKey(Posc.get(), "sword-bandit"), new NamespacedKey(Posc.get(), "sword-block"), new NamespacedKey(Posc.get(), "sword-blinding"), new NamespacedKey(Posc.get(), "sword-feast"), new NamespacedKey(Posc.get(), "sword-hulk"), new NamespacedKey(Posc.get(), "sword-lifesteal"), new NamespacedKey(Posc.get(), "sword-milk")};

    public static byte getCELevel(ItemStack item, NamespacedKey key) {
        if (item == null) return 0;
        if (item.getItemMeta() == null) return 0;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (container.isEmpty()) return 0;
        return container.getOrDefault(key, PersistentDataType.BYTE, (byte) 0);
    }

    public static void setCELevel(ItemStack item, NamespacedKey key, byte level) {
        List<String> lore = item.getLore() == null ? new ArrayList<>() : item.getLore();
        if (getCELevel(item, key) == 0) lore.add(Methods.cStr("&9" + CENameFromKey(key) + " I"));
        else {
            lore.set(lore.indexOf(Methods.cStr("&9" + CENameFromKey(key) + " " + Methods.toRomanNumeral((getCELevel(item, key))))),
                    Methods.cStr("&9" + CENameFromKey(key) + " " + Methods.toRomanNumeral((byte) (getCELevel(item, key) + 1))));
        }
        item.setLore(lore);

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, level);
        if (key == pickaxeEnchantments[7]) {
            updateCounterAmount(item, false);
        }
        item.setItemMeta(meta);
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
            case "pickaxe-jackhammer" -> 10;
            case "pickaxe-vein", "sword-blinding" -> 7;
            case "pickaxe-drill", "sword-milk" -> 6;
            case "sword-viper", "sword-hulk", "sword-lifesteal" -> 5;
            case "pickaxe-experience" -> 4;
            case "pickaxe-bomb", "pickaxe-haste", "pickaxe-counter", "sword-block" -> 3;
            case "pickaxe-speed", "sword-feast" -> 2;
            case "sword-bandit" -> 1;
            default -> 0;
        };
    }

    public static String CENameFromKey(NamespacedKey key) {
        return Methods.rfStr(key.getKey().split("-")[1]);
    }

    public static double getCEPrice(ItemStack item, NamespacedKey key) {
        switch (key.getKey()) {
            case "pickaxe-vein" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 10000d;
                    case 1 -> 25000d;
                    case 2 -> 50000d;
                    case 3 -> 75000d;
                    case 4 -> 100000d;
                    case 5 -> 250000d;
                    case 6 -> 500000d;
                    default -> 0d;
                };
            }
            case "pickaxe-bomb" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 100000d;
                    case 1 -> 200000d;
                    case 2 -> 500000d;
                    default -> 0d;
                };
            }
            case "pickaxe-speed" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 25000d;
                    case 1 -> 50000d;
                    default -> 0d;
                };
            }
            case "pickaxe-haste" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 150000d;
                    case 1 -> 250000d;
                    case 2 -> 450000d;
                    default -> 0d;
                };
            }
            case "pickaxe-experience" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 200000d;
                    case 1 -> 300000d;
                    case 2 -> 500000d;
                    case 3 -> 1000000d;
                    default -> 0d;
                };
            }
            case "pickaxe-jackhammer" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 100000d;
                    case 1 -> 200000d;
                    case 2 -> 300000d;
                    case 3 -> 500000d;
                    case 4 -> 750000d;
                    case 5 -> 1000000d;
                    case 6 -> 1500000d;
                    case 7 -> 2000000d;
                    case 8 -> 2750000d;
                    case 9 -> 5000000d;
                    default -> 0d;
                };
            }
            case "pickaxe-drill" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 100000d;
                    case 1 -> 200000d;
                    case 2 -> 300000d;
                    case 3 -> 500000d;
                    case 4 -> 750000d;
                    case 5 -> 1000000d;
                    default -> 0d;
                };
            }
            case "pickaxe-counter" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 250000d;
                    case 1 -> 500000d;
                    case 2 -> 1000000d;
                    default -> 0d;
                };
            }
            case "sword-viper" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 25000d;
                    case 1 -> 50000d;
                    case 2 -> 100000d;
                    case 3 -> 200000d;
                    case 4 -> 300000d;
                    default -> 0d;
                };
            }
            case "sword-bandit" -> {
                return 50000d;
            }
            case "sword-block" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 100000d;
                    case 1 -> 200000d;
                    case 2 -> 300000d;
                    default -> 0d;
                };
            }
            case "sword-blinding" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 20000d;
                    case 1 -> 60000d;
                    case 2 -> 100000d;
                    case 3 -> 200000d;
                    case 4 -> 300000d;
                    case 5 -> 400000d;
                    case 6 -> 500000d;
                    default -> 0d;
                };
            }
            case "sword-feast" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 250000d;
                    case 1 -> 500000d;
                    default -> 0d;
                };
            }
            case "sword-hulk" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 50000d;
                    case 1 -> 100000d;
                    case 2 -> 250000d;
                    case 3 -> 450000d;
                    case 4 -> 750000d;
                    default -> 0d;
                };
            }
            case "sword-lifesteal" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 100000d;
                    case 1 -> 250000d;
                    case 2 -> 500000d;
                    case 3 -> 1000000d;
                    case 4 -> 2500000d;
                    default -> 0d;
                };
            }
            case "sword-milk" -> {
                return switch (getCELevel(item, key)) {
                    case 0 -> 100000d;
                    case 1 -> 200000d;
                    case 2 -> 300000d;
                    case 3 -> 500000d;
                    case 4 -> 750000d;
                    case 5 -> 1500000d;
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
                    case 3 -> 10000d;
                    case 4 -> 15000d;
                    case 5 -> 40000d;
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

    public static double getMaterialPrice(Material material) {
        return switch (material.name().split("_")[0]) {
            case "DIAMOND" -> 500000d;
            case "NETHERITE" -> 1000000d;
            case "GOLDEN" -> 10000000d;
            default -> 0d;
        };
    }

    public static void addLore(ItemStack item, String... strings) {
        List<String> lore = item.getLore() == null ? new ArrayList<>() : item.getLore();
        lore.addAll(List.of(strings));
        item.setLore(lore);
    }

    public static void setGUIEnchant(ItemStack item, Inventory inventory, int index, String name, Enchantment enchant, byte key, String... lore) {
        ItemStack GUIItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = GUIItem.getItemMeta();
        meta.setDisplayName(item.getEnchantmentLevel(enchant) == 0
                ? Methods.cStr(name + " &eI")
                : Methods.cStr(name + " &7" + Methods.toRomanNumeral((byte) item.getEnchantmentLevel(enchant)) + " &f→ &e" + Methods.toRomanNumeral((byte) (item.getEnchantmentLevel(enchant) + 1))));
        if (item.getEnchantmentLevel(enchant) == getEnchantmentMaxLevel(enchant)) meta.setDisplayName(Methods.cStr(name + " &6" + Methods.toRomanNumeral((byte) item.getEnchantmentLevel(enchant))));
        meta.setLore(List.of(lore));
        meta.getPersistentDataContainer().set(new NamespacedKey(Posc.get(), "gui-item"), PersistentDataType.BYTE, key);
        GUIItem.setItemMeta(meta);
        String addendum = item.getEnchantmentLevel(enchant) >= getEnchantmentMaxLevel(enchant) ? Methods.cStr("&6&lMAX LEVEL") : Methods.cStr("&eCost&8: &f$" + Methods.rStr(getEnchantmentPrice(item, enchant)));
        addLore(GUIItem, "", addendum);
        inventory.setItem(index, GUIItem);
    }

    public static void setGUICE(ItemStack item, Inventory inventory, int index, NamespacedKey key, Material material, int level, int levelReq, String... lore) {
        if (level < levelReq) {
            inventory.setItem(index, GUIHandler.quickItem(Material.BARRIER, Methods.cStr("&cLocked"), 1, Methods.cStr("&cUnlocks at &flevel " + levelReq + "&c.")));
            return;
        }

        ItemStack GUIItem = new ItemStack(material);
        ItemMeta meta = GUIItem.getItemMeta();
        meta.setDisplayName(getCELevel(item, key) == 0
                ? Methods.cStr("&e" + CENameFromKey(key) + " I")
                : Methods.cStr("&e" + CENameFromKey(key) + " &7" + Methods.toRomanNumeral(getCELevel(item, key)) + " &f→ &e" + Methods.toRomanNumeral((byte) (getCELevel(item, key) + 1))));
        if (getCELevel(item, key) == getCEMaxLevel(key)) meta.setDisplayName(Methods.cStr("&e" + CENameFromKey(key) + " &6" + Methods.toRomanNumeral(getCELevel(item, key))));
        meta.setLore(List.of(lore));
        GUIItem.setItemMeta(meta);
        String addendum = getCELevel(item, key) >= getCEMaxLevel(key) ? Methods.cStr("&6&lMAX LEVEL") : Methods.cStr("&eCost&8: &f$" + Methods.rStr(getCEPrice(item, key)));
        addLore(GUIItem, "", addendum);
        inventory.setItem(index, GUIItem);
    }

    public static void setEnchant(ItemStack item, Enchantment enchant, byte level) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchant, level, true);
        item.setItemMeta(meta);
    }

    public static double getCounterMulti(ItemStack item) {
        if (item == null) return 0;
        if (item.getItemMeta() == null) return 0;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (container.isEmpty()) return 0;
        int counter = container.getOrDefault(pickaxeCounter, PersistentDataType.INTEGER, 0);
        byte level = getCELevel(item, pickaxeEnchantments[7]);
        return Math.floor(Math.log10(Math.max(counter, 1))) * (level * 0.1);
    }

    public static void updateCounterAmount(ItemStack item, boolean addition) {
        ItemMeta meta = item.getItemMeta();
        int amount = meta.getPersistentDataContainer().getOrDefault(pickaxeCounter, PersistentDataType.INTEGER, 0);
        if (addition) amount++;
        meta.getPersistentDataContainer().set(pickaxeCounter, PersistentDataType.INTEGER, amount);
        meta.setDisplayName(Methods.cStr("&b" + Methods.getMaterialName(item.getType()) + " &7[&f" + Methods.rStr((double) amount) + "&7]"));
        item.setItemMeta(meta);
    }
}