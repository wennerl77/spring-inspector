package org.inspector.utils.color;

public class PrintColorUtil {
    public static String colorize(String text, AnsiColor color) {
        return "%s%s%s".formatted(color.code(), text, AnsiColor.RESET);
    }
}
