package dev.chel_shev.nelly.util;

import java.util.List;

import static java.util.Objects.isNull;

public class TelegramBotUtils {

    public static boolean isCommandInquiry(String text) {
        return !isNull(text) && List.of("Добавить ДР", "Удалить ДР").contains(text) ;
    }

    public static boolean isKeyboardInquiry(String text) {
        return List.of("ДР", "Финансы").contains(text);
    }

    public static String getCommand(String text) {
        return !isNull(text) ? text.split(" ")[0] : "";
    }

    public static String getArgs(String text) {
        return text.replace(getCommand(text), "").trim();
    }
}