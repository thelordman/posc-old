package me.lord.posc.event;


import me.lord.posc.Posc;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;

public abstract class AbstractEvent implements Event {
	private Instant date;
	private boolean hasStarted = false;

	protected AbstractEvent() {

	}

	@Override
	public void start() {
		Event.super.start();

		new BukkitRunnable() {
			int i = 10;
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(TextUtil.c("&e" + TextUtil.sexyString(getName()) + " Event starting in &6" + i + " seconds")));
				i--;

				if (i == 0) {
					if (task() != null) {
						task().run();
					}

					getPlayers().forEach(p -> {
						p.sendMessage(TextUtil.c("&6Event starting"));
						if (forEachPlayer() != null) {
							forEachPlayer().accept(p);
						}
					});

					date = Instant.now();
					hasStarted = true;

					cancel();
				}
			}
		}.runTaskTimer(Posc.get(), 1000L, 20L);
	}

	@Override
	public Instant getDate() {
		return date;
	}

	@Override
	public boolean hasStarted() {
		return hasStarted;
	}

	@Override
	public void end() {
		hasStarted = false;
	}
}
