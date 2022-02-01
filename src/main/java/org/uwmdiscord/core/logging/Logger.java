package org.uwmdiscord.core.logging;

import org.jetbrains.annotations.TestOnly;
import org.uwmdiscord.core.Config;

public class Logger {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void error(String message) {
        log(message, LoggerLevel.ERROR);
    }

    public static void warning(String warning) {
        log(warning, LoggerLevel.WARNING);
    }

    public static void log(String message, LoggerLevel level) {
        System.out.println(level.getColor() + "[" + level.name() + "] " + message);
    }

    public static void info(String message) {
        System.out.println(LoggerLevel.INFO.getColor() + "[INFO] " + message);
    }

    public static void debug(String debug) {
        if (Config.IS_DEBUG) log(debug, LoggerLevel.DEBUG);
    }

    /**
     * This debug method should only be used in the core functions.
     * Its use is to have many debug messages for simple things that
     * are important to the function of the entire bot.
     * @param debug - debug message
     */
    @TestOnly
    public static void deepDebug(String debug) {
        if (false) log(debug, LoggerLevel.DEBUG);
    }
}
