package io.github.thelordman.costrength;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.CommandHandler;
import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
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
                EconomyManager.setBalance(online.getUniqueId(), (float) online.getStatistic(Statistic.USE_ITEM, Material.GOLD_NUGGET));
                EconomyManager.setBounty(online.getUniqueId(), (float) online.getStatistic(Statistic.USE_ITEM, Material.SPYGLASS));
                EconomyManager.setXp(online.getUniqueId(), (float) online.getStatistic(Statistic.USE_ITEM, Material.EXPERIENCE_BOTTLE));
                EconomyManager.setLevel(online.getUniqueId(), online.getStatistic(Statistic.USE_ITEM, Material.FIREWORK_ROCKET));
                EconomyManager.setKillstreak(online.getUniqueId(), online.getStatistic(Statistic.USE_ITEM, Material.WOODEN_SWORD));

                ScoreboardHandler.updateBoard(online);
                Data.scoreboard.get(online.getUniqueId()).updateTitle(Methods.cStr("&6&lCoStrength &7(" + Bukkit.getOnlinePlayers().size() + "&7/" + Bukkit.getMaxPlayers() + "&7)"));
            }
            getLogger().info("Data loaded");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Executing onDisable method");

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.setStatistic(Statistic.USE_ITEM, Material.GOLD_NUGGET, EconomyManager.getBalance(online.getUniqueId()).intValue());
                online.setStatistic(Statistic.USE_ITEM, Material.SPYGLASS, EconomyManager.getBounty(online.getUniqueId()).intValue());
                online.setStatistic(Statistic.USE_ITEM, Material.EXPERIENCE_BOTTLE, EconomyManager.getXp(online.getUniqueId()).intValue());
                online.setStatistic(Statistic.USE_ITEM, Material.FIREWORK_ROCKET, EconomyManager.getLevel(online.getUniqueId()));
                online.setStatistic(Statistic.USE_ITEM, Material.WOODEN_SWORD, EconomyManager.getKillstreak(online.getUniqueId()));
            }
        }
        getLogger().info("Data saved");

        Discord.shutdownJDA();
    }
}