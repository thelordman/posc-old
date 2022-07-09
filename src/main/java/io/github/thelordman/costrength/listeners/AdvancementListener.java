package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.utilities.Methods;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener {

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        if(event.getAdvancement().getKey().getKey().contains("quests/") && Bukkit.getUnsafe().legacyComponentSerializer().serialize(event.getAdvancement().getDisplay().description()).contains("$")) {
            event.message(Component.text(Methods.cStr(event.getPlayer().getDisplayName() + " &6has completed a quest and received &e$" + Bukkit.getUnsafe().legacyComponentSerializer().serialize(event.getAdvancement().getDisplay().description()).split("[$]")[1].replaceAll("!", "") + "&6!")));
        }
    }
}
