package me.lord.posc.discord.listeners;

import com.sun.management.OperatingSystemMXBean;
import me.lord.posc.Posc;
import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.PoscEmbedBuilder;
import me.lord.posc.utilities.TextUtil;
import me.lord.posc.utilities.tuples.Triplet;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.time.Instant;

public class SlashCommandInteractionListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        Member member = event.getMember();

        switch (event.getName()) {
            case "ping" -> {
                long time = Instant.now().toEpochMilli();
                event.replyEmbeds(new PoscEmbedBuilder()
                        .setAuthor("Pong!")
                        .setDescription("**Ping:** Calculating...")
                        .setColor(Color.BLUE)
                        .build()
                ).flatMap(v ->
                            event.getHook().editOriginalEmbeds(
                                    new PoscEmbedBuilder()
                                            .setAuthor("Pong!")
                                            .setDescription("**Ping:** " + TextUtil.format(Instant.now().toEpochMilli() - time) + " ms")
                                            .setColor(Color.BLUE)
                                            .build()
                            )
                ).queue();
            }
            case "server" -> {
                // TODO: Sort players by rank
                Triplet<String, Integer, Double> stats = getPlayers();
                String players = stats.getA();
                int playerAmount = stats.getB();
                double totalPing = stats.getC();

                String statistics = "**Online Players:** " + playerAmount
                        + "\n**TPS:**"
                            + "\n   1 m: " + TextUtil.format(Bukkit.getTPS()[0])
                            + "\n   5 m: " + TextUtil.format(Bukkit.getTPS()[1])
                            + "\n   15 m: " + TextUtil.format(Bukkit.getTPS()[2])
                        + "\n**Average Ping:** " + TextUtil.format(totalPing / playerAmount) + " ms"
                        + "\n**Memory:** " + TextUtil.format((int) ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1000000)) + " MB/" + TextUtil.format((int) (Runtime.getRuntime().maxMemory() / 1000000)) + " MB"
                        + "\n**CPU:** " + TextUtil.format(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getCpuLoad() * 100) + "%";

                event.replyEmbeds(new PoscEmbedBuilder()
                        .setAuthor("Online Players")
                        .setDescription(players)
                        .addField("Statistics", statistics, false)
                        .setColor(Color.BLUE)
                        .build()).queue();
            }
            case "online" -> {
                Triplet<String, Integer, Double> stats = getPlayers();
                String players = stats.getA();
                int playerAmount = stats.getB();

                event.replyEmbeds(new PoscEmbedBuilder()
                        .setAuthor("Online Players")
                        .setDescription(players + "\n**Total: ** " + playerAmount)
                        .setColor(Color.BLUE)
                        .build()).queue();
            }
            case "command" -> {
                if (!member.getRoles().contains(Discord.jda.getRoleById("921439677574160499"))) {
                    event.reply("You aren't allowed to execute this command.").setEphemeral(true).queue();
                    return;
                }
                String command = event.getOption("command", OptionMapping::getAsString);
                if (command == null) {
                    event.reply("You must state a command to execute.").setEphemeral(true).queue();
                    return;
                }
                Bukkit.getScheduler().callSyncMethod(Posc.get(), () ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
                event.replyEmbeds(new PoscEmbedBuilder()
                        .setAuthor("Command")
                        .setDescription("Successfully executed the console command `/" + command + "`")
                        .setColor(Color.BLUE)
                        .build()).queue();
            }
        }
    }

    private Triplet<String, Integer, Double> getPlayers() {
        int playerAmount = 0;
        double totalPing = 0d;

        StringBuilder builder = new StringBuilder();

        for (Player player : Bukkit.getOnlinePlayers()) {
            playerAmount++;
            totalPing += player.getPing();
            builder.append(TextUtil.stripColorCodes(TextUtil.componentToString(player.displayName()))).append("\n");
        }

        return new Triplet<>(builder.toString(), playerAmount, totalPing);
    }
}
