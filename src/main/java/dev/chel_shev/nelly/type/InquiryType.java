package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum InquiryType {
    EXPENSE("Расход", "/expense"),
    INCOME("Доход", "/income"),
    LOAN("Займ", "/loan"),
    TRANSFER("Перевод", "/transfer"),
    BDAY("Добавить ДР", "/bday"),
    BDAY_REMOVE("Удалить ДР", "/bday_remove"),
    START("Старт", "/start"),
    STOP("Стоп", "/stop"),
    WORKOUT("Воркаут", "/workout"),
    NONE("Нет", "/none"),
    UNKNOWN("Неизвестно", "/unknown"),
    UNKNOWN_USER("Неизвестный пользователь", "/unknown_user"),
    KEYBOARD("Клавиатура", "/keyboard");

    @Getter
    public final String label;
    @Getter
    public final String command;

    InquiryType(String label, String command) {
        this.label = label;
        this.command = command;
    }

    public static final Map<String, InquiryType> ACTION_LABEL_MAP = new HashMap<>();
    public static final Map<String, InquiryType> ACTION_COMMAND_MAP = new HashMap<>();

    static {
        for (InquiryType env : values()) {
            ACTION_LABEL_MAP.put(env.label, env);
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