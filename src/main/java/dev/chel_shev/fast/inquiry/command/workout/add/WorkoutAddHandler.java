package dev.chel_shev.fast.inquiry.command.workout.add;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.service.FastCommandService;
import dev.chel_shev.fast.service.FastUserSubscriptionService;
import dev.chel_shev.fast.service.WorkoutEventService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.type.FastPeriodType;
import dev.chel_shev.fast.type.SubscriptionType;
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
    private final FastCommandService commandService;

    @Override
    public void preparationLogic(WorkoutAddInquiry i, Message message) {
        if (isNullOrEmpty(i.getWorkoutName())) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.THIRD, workoutAddConfig));
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtons(service.getAvailableWorkouts(i.getUser().getChatId()));
        }
    }

    public void inlineExecutionLogic(WorkoutAddInquiry i, CallbackQuery cq) {
        WorkoutEntity workout = service.getByName(i.getWorkoutName());
        FastCommandEntity command = commandService.getCommandByLabelOrName(i.getWorkoutName());
        FastCommandEntity parentCommand = commandService.getCommand("/workout");
        FastWorkoutEventEntity entity = new FastWorkoutEventEntity(-1, 1, command, workout, i.getPeriodType(), i.getWorkoutTime(), subscriptionService.getSubscription(i.getUser(), parentCommand, SubscriptionType.MAIN));
        subscriptionService.addSubscription(i.getUser(), command, parentCommand, SubscriptionType.SUB, i.getWorkoutName());
        eventService.save(entity);
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, workoutAddConfig));
        i.setClosed(true);
        i.setKeyboardType(FastKeyboardType.INLINE);
        i.setKeyboardButtons(new ArrayList<>());
    }

    @Override
    public void inlinePreparationLogic(WorkoutAddInquiry i, CallbackQuery cq) {
        if (isNullOrEmpty(i.getWorkoutName())) {
            i.setWorkoutName(cq.getData());
            i.setAnswerMessage("С какого дня начнем?");
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtons(DateTimeUtils.getDayOfWeek());
        } else if (isNull(i.getWorkoutTime())) {
            WorkoutEntity workout = service.getByName(i.getWorkoutName());
            i.setWorkoutTime(DateTimeUtils.getTimeFromTimeout(cq.getData(), workout, ZoneOffset.of("+3")));
            i.setAnswerMessage("Выбери период занятий:");
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtons(DateTimeUtils.getPeriodList());
        } else
            i.setPeriodType(FastPeriodType.getByLabel(cq.getData()));
    }
}
