package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum KeyboardType {

    NONE(""),
    FINANCE("Финансы \uD83D\uDCB0"),
    REMINDER("Напоминания \uD83D\uDD14"),
    CANCEL(""),
    ACCOUNTS(""),
    BDAY("ДР \uD83D\uDCC6"),
    COMMON(""),

    // inline keyboard
    PERIOD("");


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