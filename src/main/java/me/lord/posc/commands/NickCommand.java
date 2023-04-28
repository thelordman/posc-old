package me.lord.posc.commands;

import me.lord.posc.utilities.Cmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// TODO: Cleanup
public class NickCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length > 0) {
			if (sender instanceof Player player)
				player.setDisplayName(args[0]);
		}
		return true;
	}

	@Override
	public String name() {
		return "nick";
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return null;
	}
}
