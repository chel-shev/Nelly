package dev.chel_shev.nelly.inquiry.utils;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.service.AccountService;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.type.PeriodType;
import dev.chel_shev.nelly.util.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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

    public static ReplyKeyboard createKeyboard(KeyboardType type, UserEntity user) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        KeyboardRow inquiriesFirstRow = new KeyboardRow();
        KeyboardRow inquiriesSecondRow = new KeyboardRow();
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);
        switch (type) {
            case COMMON -> {
                inquiriesFirstRow.addAll(Arrays.asList(FINANCE_KEY.label, BDAY_KEY.label));
                inquiriesSecondRow.addAll(Arrays.asList(REMINDER.label));
//                inquiriesSecondRow.addAll(Arrays.asList("Спорт", "Английский"));
                keyboard.setKeyboard(Arrays.asList(inquiriesFirstRow, inquiriesSecondRow));
                return keyboard;
            }
            case BDAY -> {
                inquiriesFirstRow.addAll(Arrays.asList(BDAY_ADD.keyLabel, BDAY_REMOVE.keyLabel));
                keyboard.setKeyboard(Arrays.asList(inquiriesFirstRow, getBackRow()));
                return keyboard;
            }
            case FINANCE -> {
                inquiriesFirstRow.addAll(Arrays.asList(EXPENSE.keyLabel, INCOME.keyLabel));
                inquiriesSecondRow.addAll(Arrays.asList(LOAN.keyLabel, TRANSFER.keyLabel));
                keyboard.setKeyboard(Arrays.asList(inquiriesFirstRow, inquiriesSecondRow, getBackRow()));
                return keyboard;
            }
            case REMINDER -> {
                inquiriesFirstRow.addAll(Arrays.asList(REMINDER_ADD.keyLabel, REMINDER_REMOVE.keyLabel));
                keyboard.setKeyboard(Arrays.asList(inquiriesFirstRow, getBackRow()));
                return keyboard;
            }
            case CANCEL -> {
                keyboard.setKeyboard(Collections.singletonList(getCancelRow()));
                return keyboard;
            }
            case ACCOUNTS -> {
                List<KeyboardRow> rowList = new ArrayList<>(getAccountRowList(user));
                rowList.add(getCancelRow());
                keyboard.setKeyboard(rowList);
                return keyboard;
            }
            case PERIOD -> {
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
                inlineKeyboard.setKeyboard(getButton());
                return inlineKeyboard;
            }
            case NONE -> keyboard.setKeyboard(new ArrayList<>());
            default -> throw new TelegramBotException(user, "Неверный тип клавиатуры!", KeyboardType.COMMON);
        }
        return keyboard;
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

    private static List<List<InlineKeyboardButton>> getButton() {
        List<PeriodType> periodTypes = Arrays.stream(PeriodType.values()).toList().stream().toList();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> rowNames = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < periodTypes.size(); i++) {
            if (i % 2 == 0) {
                if (i != 0) {
                    row.addAll(rowNames);
                    rows.add(row);
                }
                row = new ArrayList<>();
                rowNames = new ArrayList<>();
            }
            InlineKeyboardButton button = InlineKeyboardButton.builder().text(periodTypes.get(i).name()).callbackData(periodTypes.get(i).name()).build();
            rowNames.add(button);
        }
        row.addAll(rowNames);
        rows.add(row);
        return rows;
    }

    private static List<KeyboardRow> getAccountRowList(UserEntity user) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        AccountService accountService = (AccountService) appCtx.getBean("accountService");
        List<String> accountList = accountService.getAccountListByUserId(user.getId()).stream().map(AccountEntity::getInfoString).collect(Collectors.toList());
        List<KeyboardRow> rows = new ArrayList<>();
        List<String> rowNames = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < accountList.size(); i++) {
            if (i % 2 == 0) {
                if (i != 0) {
                    row.addAll(rowNames);
                    rows.add(row);
                }
                row = new KeyboardRow();
                rowNames = new ArrayList<>();
            }
            rowNames.add(accountList.get(i));
        }
        row.addAll(rowNames);
        rows.add(row);
        return rows;
    }
}