package me.wiceh.mafia.api.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
    private static final Logger LOGGER = Logger.getLogger("Mafia");

    public static void logError(Throwable throwable, String message) {
        LOGGER.log(Level.SEVERE, throwable, () -> message);
    }
}
