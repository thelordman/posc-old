package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.commands.LeaderboardCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (command.getName()) {
            case "rank":
                return switch (args.length) {
                    case 1 -> null;
                    case 2 -> List.of("set");
                    case 3 -> List.of("default", "vip", "mvp", "elite", "legend", "trial_developer", "jrmod", "builder",
                            "mod", "srmod", "developer", "head_builder", "admin", "head_developer", "owner");
                    default -> Collections.emptyList();
                    };
            case "leaderboard":
                if (args.length == 1) return LeaderboardCommand.completions;
        }

        return Collections.emptyList();
    }
}
