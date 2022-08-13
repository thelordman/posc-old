package io.github.thelordman.posc.date;

import org.apache.commons.lang.StringUtils;

import java.time.Instant;
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
                int[] time = {seconds % 60, (seconds % 3600) / 60, seconds / 3600};
                String[][] s = {{" seconds ", " minutes ", " hours "}, {"s ", "m ", "h "}};
                int i = dateType == DateType.HOUR_MINUTE_SECOND ? 0 : 1;
                StringBuilder builder = new StringBuilder();
                for (int ii = 2; ii > -1; ii--) {
                    builder.append(time[ii] != 0 ? time[ii] + s[i][ii] : "");
                }
                yield builder.toString();
            }
        };
    }

    public static int secsFromString(String string) {
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

    public static int nowPlus(int amount) {
        return ((int) Instant.now().getEpochSecond()) + amount;
    }
}