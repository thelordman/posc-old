package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.commands.*;
import org.bukkit.command.CommandExecutor;

import java.util.Objects;

public class CommandHandler {
    private final CoStrength plugin;

    public CommandHandler(CoStrength plugin) {
        this.plugin = plugin;

        registerCommand("gmc", new GamemodeCommands());
        registerCommand("gms", new GamemodeCommands());
        registerCommand("gma", new GamemodeCommands());
        registerCommand("gmsp", new GamemodeCommands());
        registerCommand("liami", new LiamiCommand());
        registerCommand("balance", new BalanceCommand());
        registerCommand("economy", new EconomyCommand());
        registerCommand("stats", new StatsCommand());
        registerCommand("jail", new JailCommand());
        registerCommand("broadcast", new BroadcastCommand());
        registerCommand("sun", new WeatherCommands());
        registerCommand("rain", new WeatherCommands());
        registerCommand("thunder", new WeatherCommands());
        registerCommand("spawn", new SpawnCommand());
        registerCommand("tphere", new TphereCommand());
        registerCommand("commandspy", new CommandSpyCommand());
        registerCommand("rank", new RankCommand());
        registerCommand("help", new HelpCommand());
        registerCommand("discord", new DiscordCommand());
    }

    private void registerCommand(String command, CommandExecutor executor) {
        Objects.requireNonNull(plugin.getCommand(command)).setExecutor(executor);
    }
}
