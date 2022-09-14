package io.github.thelordman.posc.food;

import io.github.thelordman.posc.Posc;
import io.github.thelordman.posc.items.ItemManager;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class FoodUtil {
    // Hunger, Saturation
    private static final HashMap<Material, Pair<Integer, Float>> vanillaFoodValue = new HashMap<>();

    static NamespacedKey HUNGER_KEY = new NamespacedKey(Posc.get(), "hunger");
    static NamespacedKey SATURATION_KEY = new NamespacedKey(Posc.get(), "saturation");
    static NamespacedKey EFFECT_NAME_KEY = new NamespacedKey(Posc.get(), "effect.name");
    static NamespacedKey EFFECT_LEVEL_KEY = new NamespacedKey(Posc.get(), "effect.level");
    static NamespacedKey EFFECT_DURATION_KEY = new NamespacedKey(Posc.get(), "effect.duration");

    static void register() {
        vanillaFoodValue.put(Material.BREAD, new Pair<>(5, 6f));
        vanillaFoodValue.put(Material.COOKED_CHICKEN, new Pair<>(6, 7.2f));
        vanillaFoodValue.put(Material.COOKED_BEEF, new Pair<>(8, 12.8f));
        vanillaFoodValue.put(Material.HONEY_BOTTLE, new Pair<>(6, 1.2f));
        vanillaFoodValue.put(Material.RABBIT_STEW, new Pair<>(10, 12f));
    }

    public static int getVanillaHunger(final ItemStack item) {
        return vanillaFoodValue.get(item).getValue0();
    }
    public static float getVanillaSaturation(final ItemStack item) {
        return vanillaFoodValue.get(item).getValue1();
    }

    static ItemStack item(final Material material, final String name, final int hunger, final float saturation, final String... lore) {
        final ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        if (hunger != 0) itemMeta.getPersistentDataContainer().set(HUNGER_KEY, PersistentDataType.INTEGER, hunger);
        if (saturation != 0f) itemMeta.getPersistentDataContainer().set(SATURATION_KEY, PersistentDataType.FLOAT, saturation);
        itemMeta.setDisplayName(Methods.cStr(name));
        List<String> list = new ArrayList<>(lore.length + 1);
        list.add(Methods.cStr((hunger == 0 ? "" : "&6Restores &f" + hunger + " hunger") +
                (hunger > 0 && saturation > 0 ? "&6and &f" : (hunger == 0 ? "&6Restores &f" : "&6.")) +
                saturation + " saturation&6."));
        for (String str : lore) {
            list.add(Methods.cStr(str));
        }
        itemMeta.setLore(list);
        item.setItemMeta(itemMeta);
        return item;
    }

    static ItemStack beerMixable(final Material material, final String name, final int hunger, final float saturation, final PotionEffect effect, final String... lore) {
        final ItemStack item = item(material, name, hunger, saturation, lore);
        ItemMeta itemMeta = item.getItemMeta();
        ItemManager.addLore(item, "&r", "&7Mixable with &6Beer&7.");
        if (effect != null) {
            itemMeta.getPersistentDataContainer().set(EFFECT_NAME_KEY, PersistentDataType.STRING, effect.getType() + ",");
            itemMeta.getPersistentDataContainer().set(EFFECT_LEVEL_KEY, PersistentDataType.INTEGER_ARRAY, new int[]{effect.getAmplifier()});
            itemMeta.getPersistentDataContainer().set(EFFECT_DURATION_KEY, PersistentDataType.INTEGER_ARRAY, new int[]{effect.getDuration()});
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    static ItemStack itemBlock(final Material material, final String name, final int levelReq, final String... lore) {
        final ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Methods.cStr(name));
        List<String> list = new ArrayList<>(lore.length);
        for (String str : lore) {
            list.add(Methods.cStr(str));
        }
        ItemManager.addLore(item, "&r", "&7Level Requirement: " + Methods.rStr((double) levelReq), "&7Placeable.");
        itemMeta.setLore(list);
        item.setItemMeta(itemMeta);
        return item;
    }

    static int getHunger(final ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(HUNGER_KEY, PersistentDataType.INTEGER, 0);
    }
    static float getSaturation(final ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(SATURATION_KEY, PersistentDataType.FLOAT, 0f);
    }
    static ArrayList<PotionEffect> getPotionEffects(final ItemStack item) {
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        ArrayList<PotionEffect> list = new ArrayList<>();
        for (int i = 0; i < container.getOrDefault(EFFECT_LEVEL_KEY, PersistentDataType.INTEGER_ARRAY, new int[]{}).length; i++) {
            try {
                list.add(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(container.get(EFFECT_NAME_KEY, PersistentDataType.STRING).split(",")[i])),
                        container.get(EFFECT_DURATION_KEY, PersistentDataType.INTEGER_ARRAY)[i], container.get(EFFECT_LEVEL_KEY, PersistentDataType.INTEGER_ARRAY)[i]));
            } catch (NullPointerException e) {
                return null;
            }
        }
        return list.isEmpty() ? null : list;
    }
}