package dev.chel_shev.nelly.util;

import static java.util.Objects.isNull;

public class TelegramBotUtil {

    public static boolean isCommandInquiry(String text) {
        return !isNull(text) && text.matches("/[0-9a-z_]*");
    }

    public static String getCommand(String text) {
        return !isNull(text) ? text.split(" ")[0] : "";
    }

    public static String getArgs(String text) {
        return text.replace(getCommand(text), "");
    }
}