package dev.chel_shev.fast.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum FastBotCommandLevel {

    FIRST("first"),
    SECOND("second"),
    THIRD("third"),
    FOURTH("fourth");

    @Getter
    public final String label;

    @Getter
    public static final Map<String, FastBotCommandLevel> COMMAND_LEVEL_MAP = new HashMap<>();

    static {
        for (FastBotCommandLevel keyboardType : values()) {
            if (!keyboardType.label.isEmpty())
                COMMAND_LEVEL_MAP.put(keyboardType.label, keyboardType);
        }
    }

    FastBotCommandLevel(String label) {
        this.label = label;
    }
}