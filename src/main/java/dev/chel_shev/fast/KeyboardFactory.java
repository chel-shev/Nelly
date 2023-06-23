package dev.chel_shev.fast;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public final class KeyboardFactory {

//    private final WorkoutService workoutService;
//    private final RightService rightService;
//
//    public ReplyKeyboard createKeyboard(KeyboardType type, UserEntity user) {
//        return createKeyboard(type, user, null);
//    }
//
//    public ReplyKeyboard createKeyboard(KeyboardType type, UserEntity user, EventEntity event) {
//        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
//        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
//        keyboard.setSelective(true);
//        keyboard.setResizeKeyboard(true);
//        keyboard.setOneTimeKeyboard(false);
//        switch (type) {
//            case COMMON -> {
//                List<KeyboardRow> buttons = getButtons(user, true, FINANCE, BDAY, WORKOUT, REMINDER, YOUTUBE);
//                keyboard.setKeyboard(buttons);
//                return keyboard;
//            }
//            case WORKOUT -> {
//                List<KeyboardRow> buttons = getButtons(WORKOUT_ADD, WORKOUT_REMOVE);
//                buttons.add(getBackRow());
//                keyboard.setKeyboard(buttons);
//                return keyboard;
//            }
//            case BDAY -> {
//                List<KeyboardRow> buttons = getButtons(BDAY_ADD, BDAY_REMOVE);
//                buttons.add(getBackRow());
//                keyboard.setKeyboard(buttons);
//                return keyboard;
//            }
//            case FINANCE -> {
//                List<KeyboardRow> buttons = getButtons(EXPENSE, INCOME, LOAN, TRANSFER);
//                buttons.add(getBackRow());
//                keyboard.setKeyboard(buttons);
//                return keyboard;
//            }
//            case REMINDER -> {
//                List<KeyboardRow> buttons = getButtons(REMINDER_ADD, REMINDER_REMOVE);
//                buttons.add(getBackRow());
//                keyboard.setKeyboard(buttons);
//                return keyboard;
//            }
//            case CANCEL -> {
//                keyboard.setKeyboard(Collections.singletonList(getCancelRow()));
//                return keyboard;
//            }
//            case ACCOUNT_LIST -> {
//                ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
//                AccountService accountService = (AccountService) appCtx.getBean("accountService");
//                List<String> accountList = accountService.getAccountListByUserId(user.getId()).stream().map(AccountEntity::getInfoString).collect(Collectors.toList());
//                List<KeyboardRow> rowList = new ArrayList<>(getButtons(accountList));
//                rowList.add(getCancelRow());
//                keyboard.setKeyboard(rowList);
//                return keyboard;
//            }
//            case WORKOUT_LIST -> {
//                inlineKeyboard.setKeyboard(getInlineButtons(workoutService.getUnusedWorkout(user)));
//                return inlineKeyboard;
//            }
//            case DAY_OF_WEEK_LIST -> {
//                inlineKeyboard.setKeyboard(getInlineButtons(Arrays.stream(DayOfWeekRu.values()).toList().stream().map(e -> String.valueOf(e.getShortName())).toList()));
//                return inlineKeyboard;
//            }
//            case PERIOD_LIST -> {
//                inlineKeyboard.setKeyboard(getInlineButtons(Arrays.stream(PeriodType.values()).toList().stream().map(PeriodType::getLabel).toList()));
//                return inlineKeyboard;
//            }
//            case WORKOUT_PROCESS -> {
//                inlineKeyboard.setKeyboard(getWorkoutProcess(event));
//                return inlineKeyboard;
//            }
//            case NONE -> keyboard.setKeyboard(new ArrayList<>());
//            default -> throw new TelegramBotException(user, "Неверный тип клавиатуры!", KeyboardType.COMMON);
//        }
//        return keyboard;
//    }
//
//    private KeyboardRow getCancelRow() {
//        KeyboardRow cancel = new KeyboardRow();
//        cancel.addAll(Collections.singletonList(CANCEL.label));
//        return cancel;
//    }
//
//    private KeyboardRow getBackRow() {
//        KeyboardRow back = new KeyboardRow();
//        back.addAll(Collections.singletonList(BACK.label));
//        return back;
//    }
//
//    private List<KeyboardRow> getButtons(UserEntity user, Boolean checkRight, KeyboardType... buttons) {
//        List<String> strings;
//        if (checkRight)
//            strings = Stream.of(buttons).filter(e -> rightService.isAvailable(user, e.resource)).map(KeyboardType::getLabel).toList();
//        else
//            strings = Stream.of(buttons).map(KeyboardType::getLabel).toList();
//        return getButtons(strings);
//    }
//
//    private List<KeyboardRow> getButtons(InquiryType... buttons) {
//        List<String> strings = Stream.of(buttons).map(InquiryType::getKeyLabel).toList();
//        return getButtons(strings);
//    }
}