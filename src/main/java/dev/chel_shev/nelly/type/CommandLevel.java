package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum CommandLevel {

    FIRST("first"),
    SECOND("second"),
    THIRD("third"),
    FOURTH("fourth");

    @Getter
    public final String label;

    @Getter
    public static final Map<String, CommandLevel> COMMAND_LEVEL_MAP = new HashMap<>();

    static {
        for (CommandLevel keyboardType : values()) {
            if (!keyboardType.label.isEmpty())
                COMMAND_LEVEL_MAP.put(keyboardType.label, keyboardType);
        }
    }

    CommandLevel(String label) {
        this.label = label;
    }
}