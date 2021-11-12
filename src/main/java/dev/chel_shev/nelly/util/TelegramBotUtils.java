package dev.chel_shev.nelly.util;

import java.util.List;

import static dev.chel_shev.nelly.type.InquiryType.ACTION_LABEL_MAP;
import static java.util.Objects.isNull;

public class TelegramBotUtils {

    public static boolean isCommandInquiry(String text) {
        return !isNull(text) && ACTION_LABEL_MAP.containsKey(text);
    }

    public static boolean isKeyboardInquiry(String text) {
        return List.of("ДР", "Финансы", "Назад").contains(text);
    }

    public static String getArgs(String text) {
        String s = ACTION_LABEL_MAP.keySet().stream().filter(text::contains).findFirst().orElse("");
        return text.replace(s, "").trim();
    }
}