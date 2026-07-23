package org.inspector.utils.color;

public enum AnsiColor {
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    PURPLE(35),
    CYAN(36),
    WHITE(37),

    BRIGHT_BLACK(90),
    BRIGHT_RED(91),
    BRIGHT_GREEN(92),
    BRIGHT_YELLOW(93),
    BRIGHT_BLUE(94),
    BRIGHT_PURPLE(95),
    BRIGHT_CYAN(96),
    BRIGHT_WHITE(97);

    private final String code;

    AnsiColor(int colorCode) {
        this.code = "\u001B[" + colorCode + "m";
    }

    public String code() {
        return code;
    }

    public static final String RESET = "\u001B[0m";
}