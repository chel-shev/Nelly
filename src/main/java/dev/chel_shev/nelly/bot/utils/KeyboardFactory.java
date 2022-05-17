package dev.chel_shev.nelly.bot.utils;

import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.entity.finance.AccountEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.service.AccountService;
import dev.chel_shev.nelly.service.RightService;
import dev.chel_shev.nelly.service.WorkoutService;
import dev.chel_shev.nelly.type.DayOfWeekRu;
import dev.chel_shev.nelly.type.InquiryType;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.chel_shev.nelly.type.InquiryType.*;
import static dev.chel_shev.nelly.type.KeyboardType.*;


@Component
@RequiredArgsConstructor
public final class KeyboardFactory {

    private final WorkoutService workoutService;
    private final RightService rightService;

    public ReplyKeyboard createKeyboard(KeyboardType type, UserEntity user) {
        return createKeyboard(type, user, null);
    }

    public ReplyKeyboard createKeyboard(KeyboardType type, UserEntity user, EventEntity event) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);
        switch (type) {
            case COMMON -> {
                List<KeyboardRow> buttons = getButtons(user, true, FINANCE, BDAY, WORKOUT, REMINDER, YOUTUBE);
                keyboard.setKeyboard(buttons);
                return keyboard;
            }
            case WORKOUT -> {
                List<KeyboardRow> buttons = getButtons(WORKOUT_ADD, WORKOUT_REMOVE);
                buttons.add(getBackRow());
                keyboard.setKeyboard(buttons);
                return keyboard;
            }
            case BDAY -> {
                List<KeyboardRow> buttons = getButtons(BDAY_ADD, BDAY_REMOVE);
                buttons.add(getBackRow());
                keyboard.setKeyboard(buttons);
                return keyboard;
            }
            case FINANCE -> {
                List<KeyboardRow> buttons = getButtons(EXPENSE, INCOME, LOAN, TRANSFER);
                buttons.add(getBackRow());
                keyboard.setKeyboard(buttons);
                return keyboard;
            }
            case REMINDER -> {
                List<KeyboardRow> buttons = getButtons(REMINDER_ADD, REMINDER_REMOVE);
                buttons.add(getBackRow());
                keyboard.setKeyboard(buttons);
                return keyboard;
            }
            case CANCEL -> {
                keyboard.setKeyboard(Collections.singletonList(getCancelRow()));
                return keyboard;
            }
            case ACCOUNT_LIST -> {
                ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
                AccountService accountService = (AccountService) appCtx.getBean("accountService");
                List<String> accountList = accountService.getAccountListByUserId(user.getId()).stream().map(AccountEntity::getInfoString).collect(Collectors.toList());
                List<KeyboardRow> rowList = new ArrayList<>(getButtons(accountList));
                rowList.add(getCancelRow());
                keyboard.setKeyboard(rowList);
                return keyboard;
            }
            case WORKOUT_LIST -> {
                inlineKeyboard.setKeyboard(getInlineButtons(workoutService.getUnusedWorkout(user)));
                return inlineKeyboard;
            }
            case DAY_OF_WEEK_LIST -> {
                inlineKeyboard.setKeyboard(getInlineButtons(Arrays.stream(DayOfWeekRu.values()).toList().stream().map(e -> String.valueOf(e.getShortName())).toList()));
                return inlineKeyboard;
            }
            case PERIOD_LIST -> {
                inlineKeyboard.setKeyboard(getInlineButtons(Arrays.stream(PeriodType.values()).toList().stream().map(PeriodType::getLabel).toList()));
                return inlineKeyboard;
            }
            case WORKOUT_PROCESS -> {
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
        int amountExercises = workoutEvent.getWorkout().getCountExercise();
        int step = workoutEvent.getStep();
        rowAction.add(InlineKeyboardButton.builder().text(INLINE_CANCEL.label).callbackData(INLINE_CANCEL.label).build());
        if (amountExercises > 0 && step == -1) {
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_START.label).callbackData(INLINE_NEXT.label).build());
        } else if (amountExercises > step + 1 && step > 0) {
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_PREV.label).callbackData(INLINE_PREV.label).build());
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_NEXT.label).callbackData(INLINE_NEXT.label).build());
        } else if (amountExercises > step + 1) {
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_NEXT.label).callbackData(INLINE_NEXT.label).build());
        } else {
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_PREV.label).callbackData(INLINE_PREV.label).build());
            rowAction.add(InlineKeyboardButton.builder().text(INLINE_DONE.label).callbackData(INLINE_DONE.label).build());
        }
        rows.add(rowAction);
        return rows;
    }

    private List<KeyboardRow> getButtons(UserEntity user, Boolean checkRight, KeyboardType... buttons) {
        List<String> strings;
        if (checkRight)
            strings = Stream.of(buttons).filter(e -> rightService.isAvailable(user, e.resource)).map(KeyboardType::getLabel).toList();
        else
            strings = Stream.of(buttons).map(KeyboardType::getLabel).toList();
        return getButtons(strings);
    }

    private List<KeyboardRow> getButtons(InquiryType... buttons) {
        List<String> strings = Stream.of(buttons).map(InquiryType::getKeyLabel).toList();
        return getButtons(strings);
    }

    private List<KeyboardRow> getButtons(List<String> titles) {
        List<KeyboardRow> rows = new ArrayList<>();
        List<String> rowNames = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < titles.size(); i++) {
            if (i % 2 == 0) {
                if (i != 0) {
                    row.addAll(rowNames);
                    rows.add(row);
                }
                row = new KeyboardRow();
                rowNames = new ArrayList<>();
            }
            rowNames.add(titles.get(i));
        }
        row.addAll(rowNames);
        rows.add(row);
        return rows;
    }

    private List<List<InlineKeyboardButton>> getInlineButtons(List<String> titles) {
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
}