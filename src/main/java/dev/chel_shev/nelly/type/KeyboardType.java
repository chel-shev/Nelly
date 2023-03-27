package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum KeyboardType {

    REMINDER("\uD83D\uDD14 Напоминания", "Reminder"),
    BDAY("\uD83D\uDCC6 ДР", "Birthday"),
    YOUTUBE("\uD83C\uDF9E Youtube","Youtube"),
    BACK("⬅ Назад", ""),
    COMMON("", ""),
    NONE("", ""),

    // inline keyboard
    WORKOUT_PROCESS("", ""),
    INLINE_CANCEL("⏹", ""),
    INLINE_NEXT("⏩", ""),
    INLINE_START("▶", ""),
    INLINE_PREV("⏪", ""),
    INLINE_DONE("✅", ""),

    // list keyboard
    ACCOUNT_LIST("", ""),
    WORKOUT_LIST("", ""),
    DAY_OF_WEEK_LIST("", ""),
    TIMEOUT_LIST("", ""),
    PERIOD_LIST("", "");

    @Getter
    public final String label;

    @Getter
    public final String resource;

    public static final Map<String, KeyboardType> KEYBOARD_LABEL_MAP = new HashMap<>();

    static {
        for (KeyboardType keyboardType : values()) {
            if (!keyboardType.label.isEmpty())
                KEYBOARD_LABEL_MAP.put(keyboardType.label, keyboardType);
        }
    }

    KeyboardType(String label, String resource) {
        this.label = label;
        this.resource = resource;
    }

    public static KeyboardType getFromLabel(String label) {
        return KEYBOARD_LABEL_MAP.get(label);
    }
}