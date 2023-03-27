package dev.chel_shev.fast.inquiry.command.workout.add;

import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.service.FastUserSubscriptionService;
import dev.chel_shev.fast.service.WorkoutEventService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.type.FastPeriodType;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.service.WorkoutService;
import dev.chel_shev.nelly.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.ZoneOffset;
import java.util.ArrayList;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutAddHandler extends FastInquiryHandler<WorkoutAddInquiry> {

    private final WorkoutAddConfig workoutAddConfig;
    private final WorkoutService service;
    private final WorkoutEventService eventService;
    private final FastUserSubscriptionService subscriptionService;

    @Override
    public void preparationLogic(WorkoutAddInquiry i, Message message) {
        if (isNullOrEmpty(i.getWorkoutName())) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.THIRD, workoutAddConfig));
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtonList(service.getAvailableWorkouts(i.getUser().getChatId()));
        }
    }

    public void inlineExecutionLogic(WorkoutAddInquiry i, CallbackQuery callbackQuery) {
        WorkoutEntity workout = service.getByName(i.getWorkoutName());
        FastWorkoutEventEntity entity = new FastWorkoutEventEntity(-1, 1, i.getCommand(), workout, i.getPeriodType(), i.getWorkoutTime(), subscriptionService.getSubscription(i.getUser(), i.getCommand()));
        eventService.save(entity);
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, workoutAddConfig));
        i.setClosed(true);
        i.setKeyboardType(FastKeyboardType.INLINE);
        i.setKeyboardButtonList(new ArrayList<>());
    }

    @Override
    public void inlinePreparationLogic(WorkoutAddInquiry i, CallbackQuery callbackQuery) {
        if (isNullOrEmpty(i.getWorkoutName())) {
            i.setWorkoutName(callbackQuery.getData());
            i.setAnswerMessage("Через сколько дней начнем?");
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtonList(new ArrayList<>());//DAY_OF_WEEK_LIST
        } else if (isNull(i.getWorkoutTime())) {
            WorkoutEntity workout = service.getByName(i.getWorkoutName());
            i.setWorkoutTime(DateTimeUtils.getTimeFromTimeout(callbackQuery.getData(), workout, ZoneOffset.of("+3")));
            i.setAnswerMessage("Выбери период занятий:");
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtonList(new ArrayList<>());//PERIOD_LIST
        } else
            i.setPeriodType(FastPeriodType.getByLabel(callbackQuery.getData()));
    }
}
