package me.lord.posc.utilities;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;

// TODO: Add documentation to all methods
public class TextUtil {
    public static Component c(String string) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', string));
    }

    public static String formatMoney(double number) {
        return new DecimalFormat("$#,###.##").format(number);
    }

    public static String format(int number) {
        return new DecimalFormat("#,###").format(number);
    }

    public static String ordinal(int number) {
        String string = format(number);
        if (number >= 11 && number <= 13) return string + "th";
        return switch (string.charAt(string.length() - 1)) {
            case '1' -> string + "st";
            case '2' -> string + "nd";
            case '3' -> string + "rd";
            default -> string + "th";
        };
    }
}
