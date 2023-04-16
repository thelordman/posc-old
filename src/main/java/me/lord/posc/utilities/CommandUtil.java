package me.lord.posc.utilities;

import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public final class CommandUtil {
    public static boolean error(CommandSender sender, Error error) {
        sender.sendMessage(TextUtil.c("&c" + error.getMessage()));
        return true;
    }

    public enum Error {
        PLAYER_ONLY("Only players can execute this command."),
        TARGET_NOT_ONLINE("Target player must be online.");

        private final String message;

        Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static ArrayList<String> partialMatches(String token, List<String> originals) {
        return StringUtil.copyPartialMatches(token, originals, new ArrayList<>(originals.size()));
    }

    public static boolean hasFlag(String[] args, String flag) {
        return String.join(" ", args).contains(" -" + flag);
    }
}
