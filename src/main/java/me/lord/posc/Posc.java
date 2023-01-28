package me.lord.posc;

import me.lord.posc.data.DataManager;
import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.Event;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ServiceLoader;
import java.util.logging.Logger;

public final class Posc extends JavaPlugin {
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

    /**
     * A method to automate the registration of listeners.
     * When adding a new listener add the path of the class to
     * META-INF\services\me.lord.posc.utilities.Event.
     */
    private static void registerListeners() {
        ServiceLoader<Event> loader = ServiceLoader.load(Event.class, Event.class.getClassLoader());
        for (Event event : loader) {
            Bukkit.getPluginManager().registerEvents(event, get());
        }
    }

    /**
     * A method to automate the registration of commands.
     * When adding a new command add the path of the class to
     * META-INF\services\me.lord.posc.utilities.Cmd.
     * Also add the command to plugin.yml.
     */
    private static void registerCommands() throws NullPointerException {
        ServiceLoader<Cmd> loader = ServiceLoader.load(Cmd.class, Cmd.class.getClassLoader());
        for (Cmd cmd : loader) {
            if (cmd.getName() == null && cmd.getNames() == null) {
                throw new NullPointerException("getName() and getNames() cannot both return null; " + cmd.getClass().getSimpleName());
            }
            if (cmd.getNames() == null) {
                get().getCommand(cmd.getName()).setExecutor(cmd);
            } else {
                for (String name : cmd.getNames()) {
                    get().getCommand(name).setExecutor(cmd);
                }
            }
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
