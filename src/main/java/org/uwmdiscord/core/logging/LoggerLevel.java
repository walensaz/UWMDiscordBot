package org.uwmdiscord.core.logging;

public enum LoggerLevel {

    DEBUG(0, Logger.ANSI_GREEN),
    WARNING(1, Logger.ANSI_YELLOW),
    ERROR(3, Logger.ANSI_RED),
    INFO(4, Logger.ANSI_BLUE);

    private int level;
    private String color;

    LoggerLevel(int level, String color) {
        this.color = color;
        this.level = level;
    }

    public String getColor() {
        return color;
    }
}
