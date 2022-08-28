package io.github.thelordman.posc.discord.jdalisteners;

import io.github.thelordman.posc.Posc;
import io.github.thelordman.posc.discord.Discord;
import io.github.thelordman.posc.utilities.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.*;

public class SlashCommandInteractionListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!Discord.slashCommands.contains(event.getName())) return;

        switch (event.getName()) {
            case "online" -> {
                StringBuilder onlinePlayers = new StringBuilder("**Online Players:** ");
                int averagePing = 0;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    onlinePlayers.append(Methods.replaceColorCodes(player.getDisplayName(), '§')).append(", ");
                    averagePing += player.getPing();
                }
                averagePing = averagePing == 0 ? 0 : averagePing / Bukkit.getOnlinePlayers().size();
                EmbedBuilder builder = new EmbedBuilder()
                        .setAuthor("Posc", null, "https://cdn.discordapp.com/attachments/921439679574839415/1013438891526721606/posc.png")
                        .setDescription(onlinePlayers)
                        .setColor(Color.BLUE)
                        .setFooter("Posc.minehut.gg | Average Ping: " + averagePing + "ms | TPS: " + Methods.rStr(Bukkit.getTPS()[0]));
                event.replyEmbeds(builder.build()).queue();
            }
            case "cmd" -> {
                if (!event.getMember().getRoles().contains(Discord.jda.getRoleById("1007388503136534528")) && !event.getMember().getRoles().contains(Discord.jda.getRoleById("921439677590949932"))) {
                    event.reply("You need to be a Sr. Developer or higher to be able to execute this command.").setEphemeral(true).queue();
                    return;
                }
                Bukkit.getScheduler().callSyncMethod(Posc.get(), () ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), event.getOption("command").getAsString()));
                event.reply("Successfully executed the console command `/" + event.getOption("command").getAsString() + "`").queue();
            }
            case "ping" -> {
                long time = System.currentTimeMillis();
                event.reply("Pong!")
                        .flatMap(v ->
                                event.getHook().editOriginalFormat("Pong: `%dms`", System.currentTimeMillis() - time)
                        ).queue();
            }
        }
    }
}