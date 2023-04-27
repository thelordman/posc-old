package me.lord.posc;

import me.lord.posc.data.DataManager;
import me.lord.posc.data.PlayerData;
import me.lord.posc.discord.Discord;
import me.lord.posc.npc.interaction.NPCInteraction;
import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.ReflectionUtil;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class Posc extends JavaPlugin {
    private static Posc INSTANCE;

    public static Logger LOGGER;
    public static World MAIN_WORLD;
    public static World PARKOUR_WORLD;

    @Override
    public void onEnable() {
        // DO NOT CHANGE ORDER
        saveDefaultConfig();
        INSTANCE = this;
        LOGGER = getLogger();

        MAIN_WORLD = Bukkit.getWorld("world");

        DataManager.loadAll();

        if (!DataManager.getGlobal().getWorlds().contains("parkour")) {
            DataManager.getGlobal().getWorlds().add("parkour");
        }
        for (String name : DataManager.getGlobal().getWorlds()) {
            if (Bukkit.getWorld(name) != null) {
                continue;
            }
            File file = new File(name);
            if (!file.exists() || !new File(name + File.separator + "level.dat").exists()) {
                LOGGER.warning("No data for world " + name + " found, generating new world");
                Bukkit.createWorld(WorldCreator.name(name));
            } else {
                WorldCreator.name(name).createWorld();
            }
        }
        PARKOUR_WORLD = Bukkit.getWorld("parkour");

        configureServer();
        registerListeners();
        registerCommands();
        NPCInteraction.registerInteractions();
        Discord.enable();

        Bukkit.getScheduler().runTaskTimer(get(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerData data = DataManager.getPlayerData(player);
                if (data != null) {
                    data.getScoreboard().updateConstant();
                }
            }
        }, 0L, 100L);
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
            for (Class<? extends Listener> eventClass : ReflectionUtil.getSubclasses(Listener.class, "me.lord.posc.listeners")) {
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
                    get().getCommand(cmd.name()).permissionMessage(TextUtil.c("&cYou need the permission \"" + cmd.permission() + "\" to use this command."));
                    if (cmd.permission() != null) {
                        get().getCommand(cmd.name()).setPermission(cmd.permission());
                    }
                } else {
                    for (String name : cmd.names()) {
                        get().getCommand(name).setExecutor(cmd);
                        get().getCommand(name).permissionMessage(TextUtil.c("&cYou need the permission \"" + cmd.permission() + "\" to use this command."));
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

        if (PARKOUR_WORLD != null) {
            PARKOUR_WORLD.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            PARKOUR_WORLD.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            PARKOUR_WORLD.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            PARKOUR_WORLD.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        }
    }
}
