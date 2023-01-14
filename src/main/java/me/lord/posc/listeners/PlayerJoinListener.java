package me.lord.posc.listeners;

import me.lord.posc.Posc;
import me.lord.posc.data.Data;
import me.lord.posc.utilities.Event;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Event {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Data.getGlobal().incrementTotalUsers();
        Data.loadPlayerData(event.getPlayer());
        event.joinMessage(TextUtil.c("&7[&a+&7] &f" + event.getPlayer().getName() + (event.getPlayer().hasPlayedBefore() ? "" : " &8| &6" + TextUtil.ordinal(Data.getGlobal().getTotalUsers()) + " join")));
    }
}
