package me.lord.posc.commands;

import me.lord.posc.event.Event;
import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.CommandUtil;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EventCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 0) return false;

		switch (args[0]) {
			case "join" -> {
				if (!(sender instanceof Player player)) return CommandUtil.error(sender, CommandUtil.Error.PLAYER_ONLY);

				Event event = Event.getCurrent();

				if (event == null) {
					player.sendMessage(TextUtil.c("&cNo joinable event found"));
					break;
				}
				if (!event.isJoinable()) {
					player.sendMessage(TextUtil.c("&cNo joinable event found"));
					break;
				}

				event.playerJoin(player);
			}
			case "start" -> {
				if (!sender.hasPermission("posc.command.event.start")) {
					sender.sendMessage(command.permissionMessage());
					return true;
				}

				if (args.length < 2) {
					return false;
				}

				Event event = Event.fromName(args[1]);

				if (event == null) {
					sender.sendMessage(TextUtil.c("&cNo event with that name found"));
					return true;
				}

				event.start();
			}
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return switch (args.length) {
			case 1 -> CommandUtil.partialMatches(args[0], List.of("join", "start"));
			case 2 -> args[0].equals("start") ? CommandUtil.partialMatches(args[1], Arrays.stream(Event.getAll())
					.map(Event::getName)
					.toList()) : Collections.emptyList();
			default -> Collections.emptyList();
		};
	}

	@Override
	public String name() {
		return "event";
	}
}
