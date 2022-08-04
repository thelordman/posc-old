package io.github.thelordman.costrength.date;

import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.Locale;

public class Date {
    public static String dateTimeFormat(int seconds, DateType dateType) {
        return switch (dateType) {
            case FULL -> {
                LocalDateTime ldt = LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC);
                yield ldt.getHour() + ":" + ldt.getMinute() + "." + ldt.getSecond() + " " + ldt.getMonth().getDisplayName(TextStyle.FULL, Locale.UK) +
                        " " + ldt.getDayOfMonth() + ", " + ldt.getYear();
            }
            case HOUR_MINUTE_SECOND, H_M_S -> {
                int[] time = {seconds, 0, 0};
                for (int i = 0; i < 2; i++) {
                    while (time[i] > 60) {
                        time[i] -= 60;
                        time[i + 1] += 1;
                    }
                }
                String[] s = new String[2];
                switch (dateType) {
                    case HOUR_MINUTE_SECOND -> s = new String[]{" seconds ", " minutes ", " hours "};
                    case H_M_S -> s = new String[]{" s ", " m ", " h "};
                }
                StringBuilder builder = new StringBuilder();
                for (int i = 2; i > -1; i--) {
                    builder.append(time[i] != 0 ? time[i] + s[i] : "");
                }
                yield builder.toString();
            }
        };
    }

    public static int SecsFromString(String string) {
        int i;
        try {
            i = Integer.parseInt(StringUtils.chop(string));
        } catch (NumberFormatException e) {
            return 0;
        }
        if (string.endsWith("mo")) return i * 2592000;
        return switch (string.charAt(string.length() - 1)) {
            case 's' -> i;
            case 'm' -> i * 60;
            case 'h' -> i * 3600;
            case 'd' -> i * 86400;
            case 'w' -> i * 604800;
            case 'M' -> i * 2592000;
            case 'y', 'a' -> i * 31556925;
            default -> 0;
        };
    }
}