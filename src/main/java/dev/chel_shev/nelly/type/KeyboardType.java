package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum KeyboardType {

    NONE(""),
    FINANCE("\uD83D\uDCB0 Финансы"),
    REMINDER("\uD83D\uDD14 Напоминания"),
    CANCEL(""),
    BDAY("\uD83D\uDCC6 ДР"),
    COMMON(""),
    BACK("⬅ Назад"),
    WORKOUT("\uD83E\uDD38\u200D♀️ Спорт"),

    ACCOUNTS(""),
    // inline keyboard
    WORKOUT_LIST(""),
    TIMEOUT_LIST(""),
    PERIOD_LIST("");

    @Getter
    public final String label;

    public static final Map<String, KeyboardType> KEYBOARD_LABEL_MAP = new HashMap<>();

    static {
        for (KeyboardType keyboardType : values()) {
            if (!keyboardType.label.isEmpty())
                KEYBOARD_LABEL_MAP.put(keyboardType.label, keyboardType);
        }
    }

    KeyboardType(String label) {
        this.label = label;
    }

    public static KeyboardType getFromLabel(String label) {
        return KEYBOARD_LABEL_MAP.get(label);
    }
}