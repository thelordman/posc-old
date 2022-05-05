package io.github.thelordman.costrength;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.CommandHandler;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class CoStrength extends JavaPlugin {

    public static CoStrength instance;
    public static CoStrength get() {
        return instance;
    }

    @Override
    public void onLoad() {
        getLogger().info("Executing onLoad method");
    }

    @Override
    public void onEnable() {
        getLogger().info("Executing onEnable method");
        saveDefaultConfig();

        instance = this;

        Discord.loadJDA();

        String pack = getClass().getPackageName();
        for (Class<?> c : new Reflections(pack + ".listeners")
                .getSubTypesOf(Listener.class)
        ) {
            try {
                Listener listener = (Listener) c
                        .getDeclaredConstructor()
                        .newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        new CommandHandler(this);

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (EconomyManager.getBalance(online.getUniqueId()) == null) EconomyManager.setBalance(online.getUniqueId(), 0f);
                if (EconomyManager.getKillstreak(online.getUniqueId()) == null) EconomyManager.setKillstreak(online.getUniqueId(), 0);
                if (EconomyManager.getXp(online.getUniqueId()) == null) EconomyManager.setXp(online.getUniqueId(), 0f);
                if (EconomyManager.getLevel(online.getUniqueId()) == null) EconomyManager.setLevel(online.getUniqueId(), 1);
                ScoreboardHandler.updateBoard(online);
                ScoreboardHandler.scoreboard.get(online.getUniqueId()).updateTitle(Methods.cStr("&6&lCoStrength &7(" + Bukkit.getOnlinePlayers().size() + "&7/" + Bukkit.getMaxPlayers() + "&7)"));
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Executing onDisable method");
        getLogger().info("Data saved");

        Discord.shutdownJDA();
    }
}