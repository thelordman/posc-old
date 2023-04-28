package me.lord.posc.discord.listeners;

import com.sun.management.OperatingSystemMXBean;
import me.lord.posc.Posc;
import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.PoscEmbedBuilder;
import me.lord.posc.utilities.TextUtil;
import me.lord.posc.utilities.tuples.Pair;
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
				Pair<String, Double> stats = getPlayers();
				String players = stats.getA();
				double totalPing = stats.getB();

				String statistics = "**Online Players:** " + Posc.onlinePlayers
						+ "\n**TPS:**"
						+ "\n1 m: " + TextUtil.format(Bukkit.getTPS()[0])
						+ "\n5 m: " + TextUtil.format(Bukkit.getTPS()[1])
						+ "\n15 m: " + TextUtil.format(Bukkit.getTPS()[2])
						+ "\n**Average Ping:** " + TextUtil.format(totalPing / Posc.onlinePlayers) + " ms"
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
				Pair<String, Double> stats = getPlayers();
				String players = stats.getA();

				event.replyEmbeds(new PoscEmbedBuilder()
						.setAuthor("Online Players")
						.setDescription(players + "\n**Total: ** " + Posc.onlinePlayers)
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

	private Pair<String, Double> getPlayers() {
		double totalPing = 0d;

		StringBuilder builder = new StringBuilder();

		for (Player player : Bukkit.getOnlinePlayers()) {
			totalPing += player.getPing();
			builder.append(TextUtil.stripColorCodes(TextUtil.componentToString(player.displayName()))).append("\n");
		}

		return new Pair<>(builder.toString(), totalPing);
	}
}
