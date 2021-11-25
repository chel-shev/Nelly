package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum KeyboardKeyType {

    FINANCE_KEY("Финансы \uD83D\uDCB0"),
    REMINDER("Напоминания \uD83D\uDD14"),
    CANCEL_KEY("Отмена  \uD83D\uDEAB"),
    BACK_KEY("Назад ⬅"),
    BDAY_KEY("ДР \uD83D\uDCC6"),
    DEFAULT_KEY("");

    @Getter
    public final String label;

    @Getter
    public static final Map<String, KeyboardKeyType> KEYBOARD_LABEL_MAP = new HashMap<>();

    static {
        for (KeyboardKeyType keyboardType : values()) {
            if (!keyboardType.label.isEmpty())
                KEYBOARD_LABEL_MAP.put(keyboardType.label, keyboardType);
        }
    }

    KeyboardKeyType(String label) {
        this.label = label;
    }

    public static KeyboardKeyType getFromLabel(String label) {
        return KEYBOARD_LABEL_MAP.getOrDefault(label, DEFAULT_KEY);
    }
}