package io.github.thelordman.posc.food;

import io.github.thelordman.posc.Posc;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FoodUtil {
    private static final HashMap<Material, Pair<Integer, Float>> vanillaFoodValue = new HashMap<>();

    static NamespacedKey HUNGER_KEY = new NamespacedKey(Posc.get(), "hunger");
    static NamespacedKey SATURATION_KEY = new NamespacedKey(Posc.get(), "saturation");

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
        itemMeta.getPersistentDataContainer().set(HUNGER_KEY, PersistentDataType.INTEGER, hunger);
        itemMeta.getPersistentDataContainer().set(SATURATION_KEY, PersistentDataType.FLOAT, saturation);
        itemMeta.setDisplayName(Methods.cStr(name));
        List<String> list = new ArrayList<>(lore.length + 1);
        list.add(Methods.cStr("&6Restores &f" + hunger + " hunger &6and &f" + saturation + " saturation&6."));
        for (String str : lore) {
            list.add(Methods.cStr(str));
        }
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
}