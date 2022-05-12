package io.github.thelordman.costrength.discord.jdalisteners;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.utilities.Methods;
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
                        .setAuthor("CoStrength", null, "https://cdn.discordapp.com/attachments/950090391535890442/955545141467287552/CoStrength.png")
                        .setDescription(onlinePlayers)
                        .setColor(Color.BLUE)
                        .setFooter("CoStrength.minehut.gg | Average Ping: " + averagePing + "ms | TPS: " + Methods.rStr((float) Bukkit.getTPS()[0]));
                event.replyEmbeds(builder.build()).queue();
            }
            case "cmd" -> {
                if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    event.reply("You lack the permission needed for this command('Administrator').\nPlease contact either b4nter cl4us#6866 or The Lord#8349 if you believe this is a mistake.").setEphemeral(true).queue();
                    break;
                }
                Bukkit.getScheduler().callSyncMethod(CoStrength.get(), () ->
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