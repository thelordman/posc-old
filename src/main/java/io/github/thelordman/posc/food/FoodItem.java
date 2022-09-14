package io.github.thelordman.posc.food;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FoodItem {
    // Food
    public static ItemStack FRESH_BREAD = FoodUtil.item(Material.BREAD, "&fFresh Bread", 5, 6f);
    public static ItemStack HOT_WINGS = FoodUtil.item(Material.COOKED_CHICKEN, "&cHot Wings", 6, 7.2f, "&6Has a &f10% chance &6of giving &fHaste +1&6.");
    public static ItemStack WELL_DONE_STEAK = FoodUtil.item(Material.COOKED_BEEF, "&4Well Done Steak", 8, 12.8f);
    public static ItemStack BEER = FoodUtil.item(Material.HONEY_BOTTLE, "&6Beer", 6, 1.2f, "&6Can be drunk even when full to restore saturation.", "&r", "&dModifiable with &eKitchen Block");
    public static ItemStack THICK_STEW = FoodUtil.item(Material.HONEY_BOTTLE, "&3Thick Stew", 20, 20f);

    // Beer Mixables
    public static ItemStack MELON = FoodUtil.beerMixable(Material.MELON, "&2Melon", 4, 0f, null);
    public static ItemStack BERRIES = FoodUtil.beerMixable(Material.SWEET_BERRIES, "&cBerries", 0, 14f, null);
    public static ItemStack SUGAR = FoodUtil.beerMixable(Material.SUGAR, "&fSugar", 0, 0f, new PotionEffect(PotionEffectType.SPEED, 400, 0));
    public static ItemStack IRN_BRU = FoodUtil.beerMixable(Material.HONEY_BOTTLE, "&eIrn Bru", 0, 0f, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 0));

    // Blocks
    public static ItemStack KITCHEN_BLOCK = FoodUtil.itemBlock(Material.SMOKER, "&aKitchen Block", 25, "&6Allows for diverse modification", "&6of different food items.");
}