package me.lord.posc.utilities;

import org.bukkit.command.CommandSender;

public class CommandUtil {
    public static boolean error(CommandSender sender, Error error) {
        sender.sendMessage(TextUtil.c("&c" + error.getMessage()));
        return true;
    }

    public enum Error {
        PLAYER_ONLY("Only players can execute this command."),
        PLAYER_NOT_ONLINE("Target player must be online.");

        private final String message;

        private Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
