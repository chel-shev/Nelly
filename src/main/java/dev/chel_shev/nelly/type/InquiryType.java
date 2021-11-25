package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum InquiryType {
    EXPENSE("Расход \uD83D\uDCB0", "/expense"),
    INCOME("Доход \uD83D\uDCB0", "/income"),
    LOAN("Займ \uD83D\uDCB0", "/loan"),
    TRANSFER("Перевод \uD83D\uDCB0", "/transfer"),
    BDAY_ADD("Добавить \uD83D\uDCC6", "/bday_add"),
    BDAY_REMOVE("Удалить \uD83D\uDCC6", "/bday_remove"),
    REMINDER_ADD("Добавить \uD83D\uDD14", "/reminder_add"),
    REMINDER_REMOVE("Удалить \uD83D\uDD15", "/reminder_remove"),
    START("", "/start"),
    STOP("", "/stop"),
    KEYBOARD("", "/keyboard"),
    WORKOUT("", "/workout"),
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