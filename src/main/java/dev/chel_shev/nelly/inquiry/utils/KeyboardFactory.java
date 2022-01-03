package dev.chel_shev.nelly.inquiry.utils;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.service.AccountService;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static dev.chel_shev.nelly.type.InquiryType.*;
import static dev.chel_shev.nelly.type.KeyboardKeyType.*;


@Component
@RequiredArgsConstructor
public final class KeyboardFactory {

    public static ReplyKeyboardMarkup createKeyboard(KeyboardType type, UserEntity user) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow inquiriesFirstRow;
        KeyboardRow inquiriesSecondRow;
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);
        switch (type) {
            case COMMON -> {
                inquiriesFirstRow = new KeyboardRow();
                inquiriesSecondRow = new KeyboardRow();
                inquiriesFirstRow.addAll(Arrays.asList(FINANCE_KEY.label, BDAY_KEY.label));
//                inquiriesSecondRow.addAll(Arrays.asList(REMINDER.label));
//                inquiriesSecondRow.addAll(Arrays.asList("Спорт", "Английский"));
//                inquiriesSecondRow.addAll(Arrays.asList("Напоминания"));
                keyboardMarkup.setKeyboard(Arrays.asList(inquiriesFirstRow, inquiriesSecondRow));
            }
            case BDAY -> {
                inquiriesFirstRow = new KeyboardRow();
                inquiriesFirstRow.addAll(Arrays.asList(BDAY_ADD.keyLabel, BDAY_REMOVE.keyLabel));
                keyboardMarkup.setKeyboard(Arrays.asList(inquiriesFirstRow, getBackRow()));
            }
            case FINANCE -> {
                inquiriesFirstRow = new KeyboardRow();
                inquiriesSecondRow = new KeyboardRow();
                inquiriesFirstRow.addAll(Arrays.asList(EXPENSE.keyLabel, INCOME.keyLabel));
                inquiriesSecondRow.addAll(Arrays.asList(LOAN.keyLabel, TRANSFER.keyLabel));
                keyboardMarkup.setKeyboard(Arrays.asList(inquiriesFirstRow, inquiriesSecondRow, getBackRow()));
            }
            case REMINDER -> {
                inquiriesFirstRow = new KeyboardRow();
                inquiriesFirstRow.addAll(Arrays.asList(REMINDER_ADD.keyLabel, REMINDER_REMOVE.keyLabel));
                keyboardMarkup.setKeyboard(Arrays.asList(inquiriesFirstRow, getBackRow()));
            }
            case CANCEL -> keyboardMarkup.setKeyboard(Collections.singletonList(getCancelRow()));
            case ACCOUNTS -> {
                List<KeyboardRow> rowList = new ArrayList<>(getAccountRowList(user));
                rowList.add(getCancelRow());
                keyboardMarkup.setKeyboard(rowList);
            }
            case NONE -> keyboardMarkup.setKeyboard(new ArrayList<>());
            default -> throw new TelegramBotException(user, "Неверный тип клавиатуры!", KeyboardType.COMMON);
        }
        return keyboardMarkup;
    }

    private static KeyboardRow getCancelRow() {
        KeyboardRow cancel = new KeyboardRow();
        cancel.addAll(Collections.singletonList(CANCEL_KEY.label));
        return cancel;
    }

    private static KeyboardRow getBackRow() {
        KeyboardRow back = new KeyboardRow();
        back.addAll(Collections.singletonList(BACK_KEY.label));
        return back;
    }

    private static List<KeyboardRow> getAccountRowList(UserEntity user) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        AccountService accountService = (AccountService) appCtx.getBean("accountService");
        List<KeyboardRow> rowList = new ArrayList<>();
        List<String> accountList = accountService.getAccountListByUserId(user.getId()).stream().map(AccountEntity::getInfoString).collect(Collectors.toList());
        KeyboardRow row = new KeyboardRow();
        List<String> rowsName = new ArrayList<>();
        for (int i = 0; i < accountList.size(); i++) {
            if (i % 2 == 0) {
                if (i != 0) {
                    row.addAll(rowsName);
                    rowList.add(row);
                }
                row = new KeyboardRow();
                rowsName = new ArrayList<>();
            }
            rowsName.add(accountList.get(i));
        }
        row.addAll(rowsName);
        rowList.add(row);
        return rowList;
    }
}