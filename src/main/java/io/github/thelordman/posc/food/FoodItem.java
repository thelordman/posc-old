package io.github.thelordman.posc.food;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FoodItem {
    public static ItemStack BREAD = FoodUtil.item(Material.BREAD, "&fFresh Bread", 5, 6f);
    public static ItemStack HOT_WINGS = FoodUtil.item(Material.COOKED_CHICKEN, "&cHot Wings", 6, 7.2f, "&6Has a &f10% chance &6of giving &fHaste +1&6.");
    public static ItemStack WELL_DONE_STEAK = FoodUtil.item(Material.COOKED_BEEF, "&4Well Done Steak", 8, 12.8f);
    public static ItemStack BEER = FoodUtil.item(Material.HONEY_BOTTLE, "&6Beer", 6, 1.2f, "&6Can be drunk even when full to restore saturation.", "&r", "&dModifiable with &eKitchen Block");
    public static ItemStack THICK_STEW = FoodUtil.item(Material.HONEY_BOTTLE, "&3Thick Stew", 20, 20f);
}