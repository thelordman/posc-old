package me.lord.posc.economy.offer;

import me.lord.posc.data.DataManager;
import me.lord.posc.utilities.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.util.UUID;

public class SellOffer extends Offer {
	@Serial
	private static final long serialVersionUID = 3437573305369222148L;

	protected SellOffer(Material type, int amount, double value, UUID player) {
		super(type, amount, value, player);
		InventoryUtil.remove(Bukkit.getPlayer(player).getInventory(), type, amount);
	}

	@Override
	public void fill(Player player, int amount) {
		this.amount -= amount;
		DataManager.getPlayerData(player).removeBalance(value * amount);
		player.getInventory().addItem(new ItemStack(type, amount));
	}

	@Override
	public void claim() {
		DataManager.getPlayerData(player).addBalance(value * getProcessed());
		if (isComplete()) {
			getEntry().getSellOffers().remove(this);
		}
	}
}
