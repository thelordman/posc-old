package me.lord.posc;

import me.lord.posc.data.DataManager;
import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.Event;
import me.lord.posc.utilities.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class Posc extends JavaPlugin {
    private static Posc INSTANCE;

    public static Logger LOGGER;
    public static World MAIN_WORLD;

    @Override
    public void onEnable() {
        INSTANCE = this;
        LOGGER = getLogger();
        MAIN_WORLD = Bukkit.getWorld("world");

        saveDefaultConfig();

        registerListeners();
        registerCommands();
        configureServer();
        DataManager.loadAll();
        Discord.enable();
    }

    @Override
    public void onDisable() {
        DataManager.saveAll();
        Discord.disable();
    }

    public static Posc get() {
        return INSTANCE;
    }

    public static ClassLoader getInstanceClassLoader() {
        return get().getClassLoader();
    }

    /**
     * A method to automate the registration of listeners.
     */
    private static void registerListeners() {
        try {
            for (Class<? extends Event> eventClass : ReflectionUtil.getSubclasses(Event.class, "me.lord.posc.listeners")) {
                Bukkit.getPluginManager().registerEvents(eventClass.getDeclaredConstructor().newInstance(), get());
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method to automate the registration of commands.
     * Also add the command to plugin.yml.
     */
    private static void registerCommands() throws NullPointerException {
        try {
            for (Class<? extends Cmd> cmdClass : ReflectionUtil.getSubclasses(Cmd.class, "me.lord.posc.commands")) {
                Cmd cmd = cmdClass.getDeclaredConstructor().newInstance();
                if (cmd.name() == null && cmd.names() == null) {
                    throw new NullPointerException("getName() and getNames() cannot both return null; " + cmd.getClass().getSimpleName());
                }
                if (cmd.names() == null) {
                    get().getCommand(cmd.name()).setExecutor(cmd);
                    if (cmd.permission() != null) {
                        get().getCommand(cmd.name()).setPermission(cmd.permission());
                    }
                } else {
                    for (String name : cmd.names()) {
                        get().getCommand(name).setExecutor(cmd);
                        if (cmd.permission() != null) {
                            get().getCommand(name).setPermission(cmd.permissions(name));
                        }
                    }
                }
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Configures server related settings on startup to make sure
     * nothing unexpected happens.
     * This makes modifications to GameRules made with the
     * /gamerule command reset on server reload and restart.
     */
    private static void configureServer() {
        MAIN_WORLD.setDifficulty(Difficulty.HARD);

        MAIN_WORLD.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        MAIN_WORLD.setGameRule(GameRule.SPAWN_RADIUS, 0);
    }
}
