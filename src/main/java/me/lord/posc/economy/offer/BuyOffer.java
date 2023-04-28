package me.lord.posc.economy.offer;

import me.lord.posc.data.DataManager;
import me.lord.posc.utilities.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.util.UUID;

public class BuyOffer extends Offer {
    @Serial
    private static final long serialVersionUID = 4335296185688320943L;

    protected BuyOffer(Material type, int amount, double value, UUID player) {
        super(type, amount, value, player);
        DataManager.getPlayerData(player).removeBalance(value * amount);
    }

    @Override
    public void fill(Player player, int amount) {
        this.amount -= amount;
        InventoryUtil.remove(player.getInventory(), type, amount);
        DataManager.getPlayerData(player).addBalance(value * amount);
    }

    @Override
    public void claim() {
        Bukkit.getPlayer(player).getInventory().addItem(new ItemStack(type, getProcessed()));
        if (isComplete()) {
            getEntry().getBuyOffers().remove(this);
        }
    }
}
