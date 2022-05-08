package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.GUIHandler;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Food Shop") && !event.getView().getTitle().equals("Kitchen")) return;
        if (event.getCurrentItem() == null) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        float m;
        int i = event.getClick().isRightClick() ? 64 : 1;
        ItemStack item;
        String message;
        if (event.getInventory().equals(Data.foodShopGUI)) {
            switch (event.getCurrentItem().getType()) {
                case BREAD:
                    m = event.getClick().isRightClick() ? 640f : 10f;
                    item = GUIHandler.quickItem(Material.BREAD, Methods.cStr("&fFresh Bread"), i, Methods.cStr("&6Restores &f2.5 hunger &6and &f3 saturation&6."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " Fresh Bread&6.");
                    break;
                case COOKED_CHICKEN:
                    m = event.getClick().isRightClick() ? 3200f : 50f;
                    item = GUIHandler.quickItem(Material.COOKED_CHICKEN, Methods.cStr("&cHot Wings"), i, Methods.cStr("&6Restores &f3 hunger &6and &f4.5 saturation&6."), Methods.cStr("&6Has a &f10% chance &6of giving &fHaste +1&6."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &cHot Wings&6.");
                    break;
                case COOKED_BEEF:
                    m = event.getClick().isRightClick() ? 6400f : 100f;
                    item = GUIHandler.quickItem(Material.COOKED_BEEF, Methods.cStr("&4Well Done Steak"), i, Methods.cStr("&6Restores &f4 hunger &6and &f6.5 saturation&6."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &4Well Done Steak&6.");
                    break;
                case HONEY_BOTTLE:
                    m = event.getClick().isRightClick() ? 64000f : 1000f;
                    item = GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), i, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &4Beer&6.");
                    break;
                case RABBIT_STEW:
                    m = event.getClick().isRightClick() ? 320000f : 5000f;
                    item = GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&3Thick Stew"), i, Methods.cStr("&6Restores &f20 hunger &6and &f20 saturation&6."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &3Thick Stew&6.");
                    break;
                case CAULDRON:
                    GUIHandler.openGUI(Data.kitchenGUI, player);
                    player.sendMessage(Methods.cStr("&6Entered kitchen menu."));
                    return;
                case BARRIER:
                    player.sendMessage(Methods.cStr("&cYour level isn't high enough."));
                    return;
                default:
                    return;
            }
        }
        else {
            switch (event.getCurrentItem().getType()) {
                case MELON:
                    m = event.getClick().isRightClick() ? 32000f : 500f;
                    item = GUIHandler.quickItem(Material.MELON, Methods.cStr("&2Melon"), i, Methods.cStr("&6Restores &f+2 hunger &6after drinking."), "", Methods.cStr("&6Usage&7: &fCraft this with &6Beer&f."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &2Melon&6.");
                    break;
                case SWEET_BERRIES:
                    m = event.getClick().isRightClick() ? 64000f : 1000f;
                    item = GUIHandler.quickItem(Material.SWEET_BERRIES, Methods.cStr("&cBerries"), i, Methods.cStr("&6Restores &f+7 saturation &6after drinking."), "", Methods.cStr("&6Usage&7: &fCraft this with &6Beer&f."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &cBerries&6.");
                    break;
                case SUGAR:
                    m = event.getClick().isRightClick() ? 128000f : 2000f;
                    item = GUIHandler.quickItem(Material.SUGAR, Methods.cStr("&fSugar"), i, Methods.cStr("&6Gives &fspeed 1 for 20 seconds &6after drinking."), "", Methods.cStr("&6Usage&7: &fCraft this with &6Beer&f."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " Sugar&6.");
                    break;
                case POTION:
                    m = event.getClick().isRightClick() ? 256000f : 4000f;
                    item = GUIHandler.quickItem(Material.POTION, Methods.cStr("&eAlcohol"), i, Methods.cStr("&6Gives &fstrength 1 for 20 seconds &6after drinking."), "", Methods.cStr("&6Usage&7: &fCraft this with &6Beer&f."));
                    message = Methods.cStr("&6Successfully bought &f" + i + " &eAlcohol&6.");
                    break;
                case BARRIER:
                    player.sendMessage(Methods.cStr("&cYour level isn't high enough."));
                    return;
                default:
                    return;
            }
        }

        if (EconomyManager.getBalance(player.getUniqueId()) < m) {
            Methods.errorMessage("insufficientFunds", player);
            return;
        }
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
        EconomyManager.setBalance(player.getUniqueId(), EconomyManager.getBalance(player.getUniqueId()) - m);
        player.getInventory().addItem(item);
        player.sendMessage(message);
        ScoreboardHandler.updateBoard(player);
    }
}