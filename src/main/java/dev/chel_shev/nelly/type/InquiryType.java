package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum InquiryType {
    REMINDER_ADD("\uD83D\uDD14 Добавить", "/reminder_add"),
    REMINDER_REMOVE("\uD83D\uDD15 Удалить", "/reminder_remove"),
    START("", "/start"),
    STOP("", "/stop"),
    KEYBOARD("", "/keyboard"),
    NONE("", "/none"),
    UNKNOWN("", "/unknown"),
    UNKNOWN_USER("", "/unknown_user");

    @Getter
    public final String keyLabel;
    @Getter
    public final String command;

    InquiryType(String keyLabel, String command) {
        this.keyLabel = keyLabel;
        this.command = command;
    }

    public static final Map<String, InquiryType> ACTION_LABEL_MAP = new HashMap<>();
    public static final Map<String, InquiryType> ACTION_COMMAND_MAP = new HashMap<>();

    static {
        for (InquiryType env : values()) {
            ACTION_LABEL_MAP.put(env.keyLabel, env);
            ACTION_COMMAND_MAP.put(env.command, env);
        }
    }

    public static InquiryType getFromLabel(String label) {
        return ACTION_LABEL_MAP.get(label);
    }

    public static InquiryType getFromCommand(String command) {
        return ACTION_COMMAND_MAP.get(command);
    }
}