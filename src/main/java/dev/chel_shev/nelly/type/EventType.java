package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum EventType {
    BDAY("День Рождения", "/bday"),
    WORKOUT("Воркаут", "/workout");

    @Getter
    public final String keyLabel;
    @Getter
    public final String command;

    EventType(String keyLabel, String command) {
        this.keyLabel = keyLabel;
        this.command = command;
    }

    public static final Map<String, EventType> ACTION_LABEL_MAP = new HashMap<>();
    public static final Map<String, EventType> ACTION_COMMAND_MAP = new HashMap<>();

    static {
        for (EventType env : values()) {
            ACTION_LABEL_MAP.put(env.keyLabel, env);
            ACTION_COMMAND_MAP.put(env.command, env);
        }
    }

    public static EventType getFromLabel(String label) {
        return ACTION_LABEL_MAP.get(label);
    }

    public static EventType getFromCommand(String command) {
        return ACTION_COMMAND_MAP.get(command);
    }
}