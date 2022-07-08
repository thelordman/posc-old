package io.github.thelordman.costrength;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.guis.GUIHandler;
import io.github.thelordman.costrength.mining.MineHandler;
import io.github.thelordman.costrength.utilities.*;
import io.github.thelordman.costrength.utilities.data.PlayerDataManager;
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

        GUIHandler.registerInventories();
        RecipeHandler.registerRecipes();
        registerListeners();
        new CommandHandler(this);

        PlayerDataManager.loadAllPlayerData();
        getLogger().info("Data loaded");

        AdvancementHandler.init();

        MineHandler.registerRandomPattern();
    }

    @Override
    public void onDisable() {
        getLogger().info("Executing onDisable method");

        PlayerDataManager.saveAllPlayerData();
        getLogger().info("Data saved");

        Discord.shutdownJDA();
    }

    private void registerListeners() {
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
        getLogger().info("Listeners registered");
    }
}