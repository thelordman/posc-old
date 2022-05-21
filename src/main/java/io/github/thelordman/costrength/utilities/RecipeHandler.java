package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.CoStrength;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeHandler {
    public static void registerRecipes() {
        //Beer
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(CoStrength.instance, "beer_melon"), GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&2Melon&7: &6Restores &f+2 hunger &6after drinking.")));
        recipe.shape("BM");
        recipe.setIngredient('B', Data.beer);
        recipe.setIngredient('M', Material.MELON);
        Bukkit.addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(CoStrength.instance, "beer_berries"), GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&cBerries&7: &6Restores &f+7 saturation &6after drinking.")));
        recipe.shape("BM");
        recipe.setIngredient('B', Data.beer);
        recipe.setIngredient('M', Material.SWEET_BERRIES);
        Bukkit.addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(CoStrength.instance, "beer_sugar"), GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&fSugar&7: &6Gives &fspeed 1 for 20 seconds &6after drinking.")));
        recipe.shape("BM");
        recipe.setIngredient('B', Data.beer);
        recipe.setIngredient('M', Material.SUGAR);
        Bukkit.addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(CoStrength.instance, "beer_irnbru"), GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&eIrn Bru&7: &6Gives &fstrength 1 for 20 seconds &6after drinking.")));
        recipe.shape("BM");
        recipe.setIngredient('B', Data.beer);
        recipe.setIngredient('M', Material.POTION);
        Bukkit.addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(CoStrength.instance, "beer_melon_berries"), GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&2Melon&7: &6Restores &f+2 hunger &6after drinking."), Methods.cStr("&cBerries&7: &6Restores &f+7 saturation &6after drinking.")));
        recipe.shape("BM");
        recipe.setIngredient('B', GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&2Melon&7: &6Restores &f+2 hunger &6after drinking.")));
        recipe.setIngredient('M', GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&cBerries&7: &6Restores &f+7 saturation &6after drinking.")));
        Bukkit.addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(CoStrength.instance, "beer_sugar_irnbru"), GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&fSugar&7: &6Gives &fspeed 1 for 20 seconds &6after drinking."), Methods.cStr("&eIrn Bru&7: &6Gives &fstrength 1 for 20 seconds &6after drinking.")));
        recipe.shape("BM");
        recipe.setIngredient('B', GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&fSugar&7: &6Gives &fspeed 1 for 20 seconds &6after drinking.")));
        recipe.setIngredient('M', GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&eIrn Bru&7: &6Gives &fstrength 1 for 20 seconds &6after drinking.")));
        Bukkit.addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(CoStrength.instance, "beer_melon_berries_sugar_irnbru"), GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&2Melon&7: &6Restores &f+2 hunger &6after drinking."), Methods.cStr("&cBerries&7: &6Restores &f+7 saturation &6after drinking."), Methods.cStr("&fSugar&7: &6Gives &fspeed 1 for 20 seconds &6after drinking."), Methods.cStr("&eIrn Bru&7: &6Gives &fstrength 1 for 20 seconds &6after drinking.")));
        recipe.shape("BM");
        recipe.setIngredient('B', GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&2Melon&7: &6Restores &f+2 hunger &6after drinking."), Methods.cStr("&cBerries&7: &6Restores &f+7 saturation &6after drinking.")));
        recipe.setIngredient('M', GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."), "", Methods.cStr("&fSugar&7: &6Gives &fspeed 1 for 20 seconds &6after drinking."), Methods.cStr("&eIrn Bru&7: &6Gives &fstrength 1 for 20 seconds &6after drinking.")));
        Bukkit.addRecipe(recipe);
    }
}