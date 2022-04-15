package dev.chel_shev.nelly.bot.utils;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.entity.finance.AccountEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.service.AccountService;
import dev.chel_shev.nelly.service.WorkoutService;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.type.PeriodType;
import dev.chel_shev.nelly.type.TimeoutType;
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
import static dev.chel_shev.nelly.type.KeyboardType.*;


@Component
@RequiredArgsConstructor
public final class KeyboardFactory {

    private final WorkoutService workoutService;

    public ReplyKeyboard createKeyboard(KeyboardType type, UserEntity user) {
        return createKeyboard(type, user, null);
    }

    public ReplyKeyboard createKeyboard(KeyboardType type, UserEntity user, EventEntity event) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        KeyboardRow inquiriesFirstRow = new KeyboardRow();
        KeyboardRow inquiriesSecondRow = new KeyboardRow();
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);
        switch (type) {
            case COMMON -> {
                inquiriesFirstRow.addAll(Arrays.asList(FINANCE.label, BDAY.label));
//                inquiriesSecondRow.addAll(Arrays.asList(REMINDER.label));
//                inquiriesSecondRow.addAll(Arrays.asList("Спорт", "Английский"));
                inquiriesSecondRow.addAll(Arrays.asList(WORKOUT.label));
                keyboard.setKeyboard(Arrays.asList(inquiriesFirstRow, inquiriesSecondRow));
                return keyboard;
            }
            case WORKOUT -> {
                inquiriesFirstRow.addAll(Arrays.asList(WORKOUT_ADD.keyLabel, WORKOUT_REMOVE.keyLabel));
                keyboard.setKeyboard(Arrays.asList(inquiriesFirstRow, getBackRow()));
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
            case WORKOUT_LIST -> {
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
                inlineKeyboard.setKeyboard(getButtons(workoutService.getUnusedWorkout(user)));
                return inlineKeyboard;
            }
            case TIMEOUT_LIST -> {
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
                inlineKeyboard.setKeyboard(getButtons(Arrays.stream(TimeoutType.values()).toList().stream().map(e -> String.valueOf(e.getLabel())).toList()));
                return inlineKeyboard;
            }
            case PERIOD_LIST -> {
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
                inlineKeyboard.setKeyboard(getButtons(Arrays.stream(PeriodType.values()).toList().stream().map(Enum::name).toList()));
                return inlineKeyboard;
            }
            case WORKOUT_PROCESS -> {
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
                inlineKeyboard.setKeyboard(getWorkoutProcess(event));
                return inlineKeyboard;
            }
            case NONE -> keyboard.setKeyboard(new ArrayList<>());
            default -> throw new TelegramBotException(user, "Неверный тип клавиатуры!", KeyboardType.COMMON);
        }
        return keyboard;
    }

    private KeyboardRow getCancelRow() {
        KeyboardRow cancel = new KeyboardRow();
        cancel.addAll(Collections.singletonList(CANCEL.label));
        return cancel;
    }

    private KeyboardRow getBackRow() {
        KeyboardRow back = new KeyboardRow();
        back.addAll(Collections.singletonList(BACK.label));
        return back;
    }

    private List<List<InlineKeyboardButton>> getWorkoutProcess(EventEntity event) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> rowAction = new ArrayList<>();
        WorkoutEventEntity workoutEvent = (WorkoutEventEntity) event;
        if (workoutEvent.getWorkout().getExercises().size() > workoutEvent.getStep() + 1) {
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_CANCEL.label).callbackData(INLINE_CANCEL.label).build());
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_NEXT.label).callbackData(INLINE_NEXT.label).build());
        } else {
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_CANCEL.label).callbackData(INLINE_CANCEL.label).build());
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_DONE.label).callbackData(INLINE_DONE.label).build());
        }
        rows.add(rowAction);
        return rows;
    }

    private List<List<InlineKeyboardButton>> getButtons(List<String> titles) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> rowNames = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            if (i % 2 == 0) {
                if (i != 0) {
                    row.addAll(rowNames);
                    rows.add(row);
                }
                row = new ArrayList<>();
                rowNames = new ArrayList<>();
            }
            InlineKeyboardButton button = InlineKeyboardButton.builder().text(titles.get(i)).callbackData(titles.get(i)).build();
            rowNames.add(button);
        }
        row.addAll(rowNames);
        rows.add(row);
        return rows;
    }

    private List<KeyboardRow> getAccountRowList(UserEntity user) {
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