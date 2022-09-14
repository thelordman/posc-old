package io.github.thelordman.posc;

import io.github.thelordman.posc.food.FoodManager;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.discord.Discord;
import io.github.thelordman.posc.mining.MineHandler;
import io.github.thelordman.posc.utilities.*;
import io.github.thelordman.posc.utilities.data.DataManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class Posc extends JavaPlugin {

    private static Posc instance;
    public static Posc get() {
        return instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;

        Discord.loadJDA();

        registerListeners();
        registerCommands();
        FoodManager.registerFoodUtil();

        DataManager.loadAllData();

        MineHandler.registerRandomPattern();
    }

    @Override
    public void onDisable() {
        DataManager.saveAllData();

        Discord.shutdownJDA();
    }

    private void registerListeners() {
        for (Class<?> c : new Reflections(getClass().getPackageName() + ".listeners").getSubTypesOf(Listener.class)
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
    }

    private void registerCommands() {
        for (Class<?> c : new Reflections(getClass().getPackageName() + ".commands").getSubTypesOf(CommandExecutor.class)) {
            try {
                CommandExecutor executor = (CommandExecutor) c
                        .getDeclaredConstructor()
                        .newInstance();
                if (c.isAnnotationPresent(CommandName.class) && getCommand(c.getAnnotation(CommandName.class).value()) != null) {
                    getCommand(c.getAnnotation(CommandName.class).value()).setExecutor(executor);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                try {
                    TabExecutor executor = (TabExecutor) c
                            .getDeclaredConstructor()
                            .newInstance();
                    if(c.isAnnotationPresent(CommandName.class) && getCommand(c.getAnnotation(CommandName.class).value()) != null) {
                        getCommand(c.getAnnotation(CommandName.class).value()).setExecutor(executor);
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
                    getLogger().severe("Tab Executor not found!");
                }
            }
        }
    }
}