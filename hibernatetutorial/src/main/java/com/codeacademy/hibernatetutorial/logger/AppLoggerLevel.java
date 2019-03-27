package com.codeacademy.hibernatetutorial.logger;

import org.apache.log4j.Level;

@SuppressWarnings("serial")
public class AppLoggerLevel extends Level {
    public static final int APPLOGGER_INT = ERROR_INT + 10;

    public static final Level APPLOGGER = new AppLoggerLevel(APPLOGGER_INT, "APPLOGGER", 10);

    protected AppLoggerLevel(int arg0, String arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    public static Level toLevel(String logArgument) {
        if (logArgument != null && logArgument.toUpperCase().equals("APPLOGGER")) {
            return APPLOGGER;
        }
        return toLevel(logArgument, Level.DEBUG);
    }

    public static Level toLevel(int val) {
        if (val == APPLOGGER_INT) {
            return APPLOGGER;
        }
        return toLevel(val, Level.DEBUG);
    }

    public static Level toLevel(int val, Level defaultLevel) {
        if (val == APPLOGGER_INT) {
            return APPLOGGER;
        }
        return Level.toLevel(val, defaultLevel);
    }

    public static Level toLevel(String logArgument, Level defaultLevel) {
        if (logArgument != null && logArgument.toUpperCase().equals("APPLOGGER")) {
            return APPLOGGER;
        }
        return Level.toLevel(logArgument, defaultLevel);
    }
}
