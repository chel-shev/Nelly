package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum InquiryType {
    EXPENSE("\uD83D\uDCB0 Расход", "/expense"),
    INCOME("\uD83D\uDCB0 Доход", "/income"),
    LOAN("\uD83D\uDCB0 Займ", "/loan"),
    TRANSFER("\uD83D\uDCB0 Перевод", "/transfer"),
    BDAY_ADD("\uD83D\uDCC6 Добавить", "/bday_add"),
    BDAY_REMOVE("\uD83D\uDCC6 Удалить", "/bday_remove"),
    REMINDER_ADD("\uD83D\uDD14 Добавить", "/reminder_add"),
    REMINDER_REMOVE("\uD83D\uDD15 Удалить", "/reminder_remove"),
    WORKOUT_ADD("\uD83E\uDD38\u200D♀ Добавить", "/workout_add"),
    WORKOUT_REMOVE("\uD83E\uDD38\u200D♀ Удалить", "/workout_remove"),
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