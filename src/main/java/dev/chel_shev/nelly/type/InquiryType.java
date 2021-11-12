package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum InquiryType {
    EXPENSE("Расход", "/expense", "Выберите ОДНО из трёх действий:\r\n" +
            "` 1. Отправьте фото QR-кода`\r\n" +
            "` 2. Вышлите строку QR-кода`\r\n" +
            "` 3. Напишите данные о расходах в виде: «Покупка: Сумма», без кавычек`"),
    INCOME("Доход", "/income", "Напишите данные о доходах в виде:\r\n" +
            "` «Название дохода: Сумма», без кавычек`"),
    LOAN("Займ", "/loan", "Напишите данные о займе в виде:\r\n" +
            "` «Название: -Сумма», без кавычек, знак «-», если Вы даете в долг.`\r\n" +
            "\r\n" +
            "Текущие займы (₽):\r\n" +
            "%s"),
    TRANSFER("Перевод", "/transfer", "Напишите сумму перевода:\r\n"),
    BDAY("Добавить ДР", "/bday", ""),
    BDAY_REMOVE("Удалить ДР", "/bday_remove", ""),
    START("Старт", "/start", ""),
    STOP("Стоп", "/stop", ""),
    WORKOUT("Воркаут", "/workout", ""),
    NONE("Неопределено", "/none", ""),
    UNKNOWN("Неопределено", "/unknown", ""),
    UNKNOWN_USER("Неопределено", "/unknown_user", ""),
    KEYBOARD("Клавиатура", "/keyboard", "");

    @Getter
    public final String label;
    @Getter
    public final String info;
    @Getter
    public final String command;

    InquiryType(String label, String command, String info) {
        this.label = label;
        this.info = info;
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