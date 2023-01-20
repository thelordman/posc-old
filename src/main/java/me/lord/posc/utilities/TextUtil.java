package me.lord.posc.utilities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import java.text.DecimalFormat;

// TODO: Add documentation to all methods
public final class TextUtil {
    public static Component c(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
    }

    public static String componentToString(Component component) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }
    
    public static String stripColorCodes(String string) {
        return string.replaceAll("§[4c6e2ab319d5f780klmnor]", "");
    }

    public static String stripColorCodes(String string, char c) {
        return string.replaceAll(c + "[4c6e2ab319d5f780klmnor]", "");
    }

    public static String formatMoney(double number) {
        return new DecimalFormat("$#,###.##").format(number);
    }

    public static String format(int number) {
        return new DecimalFormat("#,###").format(number);
    }

    public static String ordinal(int number) {
        String string = format(number);
        if (number >= 111) number = Integer.parseInt(Character.toString(string.charAt(string.length() - 2) + string.charAt(string.length() - 1)));
        if (number >= 11 && number <= 13) return string + "th";
        return switch (string.charAt(string.length() - 1)) {
            case '1' -> string + "st";
            case '2' -> string + "nd";
            case '3' -> string + "rd";
            default -> string + "th";
        };
    }
}
