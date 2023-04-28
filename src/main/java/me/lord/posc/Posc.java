package me.lord.posc;

import com.sun.management.OperatingSystemMXBean;
import me.lord.posc.data.DataManager;
import me.lord.posc.data.PlayerData;
import me.lord.posc.discord.Discord;
import me.lord.posc.npc.interaction.NPCInteraction;
import me.lord.posc.ranks.Rank;
import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.ReflectionUtil;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Posc extends JavaPlugin {
    private static Posc instance;

    public static Logger logger;
    public static World mainWorld;
    public static World parkourWorld;

    public static int onlinePlayers;

    @Override
    public void onEnable() {
        // DO NOT CHANGE ORDER
        saveDefaultConfig();
        instance = this;
        logger = getLogger();

        mainWorld = Bukkit.getWorld("world");

        onlinePlayers = Bukkit.getOnlinePlayers().size();

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
                logger.warning("No data for world " + name + " found, generating new world");
                Bukkit.createWorld(WorldCreator.name(name));
            } else {
                WorldCreator.name(name).createWorld();
            }
        }
        parkourWorld = Bukkit.getWorld("parkour");

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
                updateTab(player);
            }
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        DataManager.saveAll();
        Discord.disable();
    }

    public static Posc get() {
        return instance;
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
        mainWorld.setDifficulty(Difficulty.HARD);
        mainWorld.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        mainWorld.setGameRule(GameRule.SPAWN_RADIUS, 0);

        if (parkourWorld != null) {
            parkourWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            parkourWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            parkourWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            parkourWorld.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        }
    }

    public static void updateTab(Player player) {
        player.sendPlayerListHeaderAndFooter(TextUtil.c("\n&6&lPosc\n"),
                TextUtil.c("\n   &6Online&7: &f" + TextUtil.format(onlinePlayers) + " &8| " +
                        "&6Ping&7: &f" + TextUtil.format(player.getPing()) + " &8| " +
                        "&6TPS&7: &f" + TextUtil.format(Bukkit.getTPS()[0]) + " &8| " +
                        "&6Memory&7: &f" + TextUtil.format((int) ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1000000)) + " MB/" + TextUtil.format((int) (Runtime.getRuntime().maxMemory() / 1000000)) + " MB" + " &8| " +
                        "&6CPU&7: &f" + TextUtil.format(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getCpuLoad() * 100) + "%   \n"));
    }

    /* public static List<Player> getSortedPlayers() {
    /     return Bukkit.getOnlinePlayers().stream().sorted((e1, e2) -> {
    /         Rank e1Rank = DataManager.getPlayerData(e1).getRank();
    /         Rank e2Rank = DataManager.getPlayerData(e2).getRank();
    /         int result = Integer.compare(e1Rank.index(), e2Rank.index());
    /         if (result == 0) {
    /             result = e1.getName().compareTo(e2.getName());
    /         }
    /         return result;
    /     }).toList();
    / }
    */
}
