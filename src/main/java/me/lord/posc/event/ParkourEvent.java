package me.lord.posc.event;

import me.lord.posc.Posc;
import me.lord.posc.data.DataManager;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

@EventName(name = "parkour")
public class ParkourEvent extends BiDailyEvent implements WorldEvent, LimitedEvent {
	@Override
	public Runnable task() {
		return () -> {
			Block[] blocks = {Posc.parkourWorld.getBlockAt(293, 11, 105), Posc.parkourWorld.getBlockAt(293, 11, 106), Posc.parkourWorld.getBlockAt(293, 11, 107)};
			for (Block block : blocks) {
				block.setType(Material.AIR);
			}
		};
	}

	private void giveKit(Player player) {
		ItemStack[] items = {new ItemStack(Material.GOLD_BLOCK), new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA), new ItemStack(Material.BARRIER)};
		ItemMeta[] itemMetas = {items[0].getItemMeta(), items[1].getItemMeta(), items[2].getItemMeta()};
		itemMetas[0].displayName(TextUtil.c("&6Last Checkpoint"));
		itemMetas[1].displayName(TextUtil.c("&dReturn To Start"));
		itemMetas[2].displayName(TextUtil.c("&cLeave Event"));
		int[] indexes = {0, 1, 8};
		for (int i = 0; i < 3; i++) {
			items[i].setItemMeta(itemMetas[i]);
			player.getInventory().setItem(indexes[i], items[i]);
		}
	}

	@Override
	public Consumer<Player> forEachPlayer() {
		return this::giveKit;
	}

	@Override
	public void playerJoin(Player player) {
		WorldEvent.super.playerJoin(player);

		DataManager.getPlayerData(player).setEventData(new Data(player));
		player.setGameMode(GameMode.ADVENTURE);

		if (hasStarted()) {
			giveKit(player);
		}
	}

	public static class Data extends WorldEvent.Data {
		private Location lastLocation;
		private Checkpoint checkpoint = Checkpoint.START;

		public Checkpoint getCheckpoint() {
			return checkpoint;
		}

		public void setCheckpoint(Checkpoint checkpoint) {
			this.checkpoint = checkpoint;
		}

		public void teleportCheckpoint() {
			checkpoint.teleport(getPlayer());
		}

		public Data(Player player) {
			super(player);
			lastLocation = player.getLocation();

			Bukkit.getScheduler().runTaskTimer(Posc.get(), (task) -> {
				if (!(DataManager.getPlayerData(player).getEventData() instanceof Data)) {
					task.cancel();
				}

				Location location = player.getLocation();

				if (lastLocation.getY() - location.getY() > 10) {
					teleportCheckpoint();
				}

				lastLocation = location;
			}, 20L, 20L);
		}

		@Override
		public Event getEvent() {
			return Event.fromClass(ParkourEvent.class);
		}
	}

	public enum Checkpoint {
		START(new Location(Posc.parkourWorld, 296, 9, 106)),
		SPRUCE(new Location(Posc.parkourWorld, 327, 13, 100)),
		TERRACOTTA(new Location(Posc.parkourWorld, 337, 16, 94)),
		PURPUR(new Location(Posc.parkourWorld, 337, 19, 94)),
		COPPER(new Location(Posc.parkourWorld, 323, 22, 89)),
		GLASS(new Location(Posc.parkourWorld, 323, 35, 100)),
		OAK(new Location(Posc.parkourWorld, 323, 40, 106)),
		AMETHYST(new Location(Posc.parkourWorld, 309, 40, 106)),
		FINISH(new Location(Posc.parkourWorld, 301, 40, 106));

		private final Location location;

		Checkpoint(Location location) {
			this.location = location;
		}

		public Location getLocation() {
			return location;
		}

		public Location getSpawn() {
			return new Location(Posc.parkourWorld, location.x(), location.y() + 1, location.z());
		}

		public void teleport(Player player) {
			player.teleport(getSpawn());
		}

		public static Checkpoint fromLocation(Location location) {
			int[] xyz = {location.getBlockX(), location.getBlockY(), location.getBlockZ()};
			for (Checkpoint checkpoint : Checkpoint.values()) {
				if (checkpoint.getLocation().getBlockX() == xyz[0] && checkpoint.getLocation().getBlockY() == xyz[1] && checkpoint.getLocation().getBlockZ() == xyz[2]) {
					return checkpoint;
				}
			}
			return null;
		}
	}

	@Override
	public World getWorld() {
		return Posc.parkourWorld;
	}
}
