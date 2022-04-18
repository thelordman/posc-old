package io.github.thelordman.costrength;

import io.github.thelordman.costrength.discord.jdalisteners.MessageReceivedListener;
import io.github.thelordman.costrength.discord.jdalisteners.SlashCommandInteractionListener;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.FastBoard;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.CommandHandler;
import io.github.thelordman.costrength.utilities.Methods;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CoStrength extends JavaPlugin {

    public static CoStrength instance;

    public static CoStrength get() {
        return instance;
    }

    public final HashMap<UUID, Double> balance = new HashMap<>();

    public static Map<UUID, FastBoard> scoreboard = new HashMap<>();

    //Discord
    public static JDA jda;

    public static TextChannel minecraftChatChannel;
    public static TextChannel commandLogChannel;

    public static ArrayList<String> slashCommands;

    @Override
    public void onLoad() {
        getLogger().info("Executing onLoad method");
    }

    @Override
    public void onEnable() {
        getLogger().info("Executing onEnable method");
        saveDefaultConfig();

        instance = this;

        loadJDA();

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
                if (EconomyManager.getBalance(online) == null) {
                    EconomyManager.setBalance(online, 0f);
                }
                if (EconomyManager.getKillstreak(online) == null) {
                    EconomyManager.setKillstreak(online, 0);
                }
                ScoreboardHandler.updateBoard(online);
                scoreboard.get(online.getUniqueId()).updateTitle(Methods.cStr("&6&lCoStrength &7(" + Bukkit.getOnlinePlayers().size() + "&7/" + Bukkit.getMaxPlayers() + "&7)"));
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Executing onDisable method");

        getLogger().info("Data saved");

        shutdownJDA();
    }

    public void loadJDA() {
        String botToken = getConfig().getString("botToken");
        String minecraftChatChannelId = getConfig().getString("minecraftChatChannelId");
        String minecraftLogChannelId = getConfig().getString("minecraftLogChannelId");
        String guildId = getConfig().getString("guildId");

        slashCommands = new ArrayList<>();
        slashCommands.add("ping");
        slashCommands.add("online");
        slashCommands.add("cmd");

        try {
            jda = JDABuilder.createLight(botToken, GatewayIntent.GUILD_MESSAGES)
                    .addEventListeners(new MessageReceivedListener())
                    .addEventListeners(new SlashCommandInteractionListener())
                    .setActivity(Activity.playing("CoStrength.minehut.gg"))
                    .build();
            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        Guild guild = jda.getGuildById(guildId);
        minecraftChatChannel = jda.getTextChannelById(minecraftChatChannelId);
        commandLogChannel = jda.getTextChannelById(minecraftLogChannelId);
        assert guild != null;
        guild.updateCommands()
                .addCommands(
                        Commands.slash("ping", "Calculate the ping of the bot. Useful for troubleshooting."),
                        Commands.slash("online", "Send a list of all online players along with some useful information about the server."),
                        Commands.slash("cmd", "Execute a console command. Only available for users with 'Administrator' permission.")
                                .addOption(OptionType.STRING, "command", "The command you want to execute. Do not include '/', unless it's a FAWE command."))
                .queue();


        if (minecraftChatChannel != null) {
            minecraftChatChannel.sendMessage(":green_circle: **Server Online**").queue();
            getLogger().info("Discord integration enabled successfully");
        }
        else {
            getLogger().severe(Methods.cStr("&cDiscord integration enabling failed: minecraftChatChannel is null"));
        }
    }

    public void shutdownJDA() {
        minecraftChatChannel.sendMessage(":red_circle: **Server Offline**").queue();
        jda.shutdown();
        getLogger().info("Discord integration disabled successfully");
    }
}