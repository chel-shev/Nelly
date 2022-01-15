package dev.chel_shev.nelly.bot.inquiry.workout;

import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.PeriodType;
import dev.chel_shev.nelly.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.google.common.base.Strings.isNullOrEmpty;
import static dev.chel_shev.nelly.type.KeyboardType.*;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutAddHandler extends InquiryHandler<WorkoutAddInquiry> {

    private final WorkoutAddConfig workoutAddConfig;

    @Override
    public void executionLogic(WorkoutAddInquiry inquiry) {
    }

    @Override
    public void preparationLogic(WorkoutAddInquiry i, Message message) {
        if (isNullOrEmpty(i.getWorkoutName())) {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.THIRD, workoutAddConfig));
            i.setKeyboardType(WORKOUT_LIST);
        }
    }

    @Override
    public void inlinePreparationLogic(WorkoutAddInquiry i, CallbackQuery callbackQuery) {
        if (isNullOrEmpty(i.getWorkoutName())) {
            i.setWorkoutName(callbackQuery.getData());
            i.setAnswerMessage("Через сколько дней начнем?");
            i.setKeyboardType(TIMEOUT_LIST);
        } else if (isNull(i.getWorkoutTime())) {
            i.setWorkoutTime(DateTimeUtils.getTimeFromTimeout(callbackQuery.getData(), i.getUser()));
            i.setAnswerMessage("Выбери период занятий:");
            i.setKeyboardType(PERIOD_LIST);
        } else
            i.setPeriodType(PeriodType.valueOf(callbackQuery.getData()));
    }
}
