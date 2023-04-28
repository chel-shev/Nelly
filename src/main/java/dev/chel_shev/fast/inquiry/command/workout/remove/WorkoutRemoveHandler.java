package dev.chel_shev.fast.inquiry.command.workout.remove;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiryHandler;
import dev.chel_shev.fast.inquiry.command.bday.remove.BdayRemoveConfig;
import dev.chel_shev.fast.inquiry.command.bday.remove.BdayRemoveInquiry;
import dev.chel_shev.fast.inquiry.command.workout.add.WorkoutAddInquiry;
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
public class WorkoutRemoveHandler extends FastCommandInquiryHandler<WorkoutRemoveInquiry> {

    private final WorkoutRemoveConfig workoutRemoveConfig;
    private final WorkoutService service;
    private final WorkoutEventService eventService;
    private final FastUserSubscriptionService subscriptionService;
    private final FastCommandService commandService;

    @Override
    public void preparationLogic(WorkoutRemoveInquiry i, Message message) {
        if (fastUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.THIRD, workoutRemoveConfig));
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtons(service.getUserWorkouts(i.getUser().getChatId()));
        } else {
            i.setMessage(fastUtils.getArgs(message.getText()));
            i.setWorkoutName(i.getArgFromMassage(message.getText(), 0));
        }
    }

    public void inlineExecutionLogic(WorkoutRemoveInquiry i, CallbackQuery cq) {
        WorkoutEntity workout = service.getByName(i.getWorkoutName());
        subscriptionService.removeSubscription(i.getUser(), workout.getCommand());
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, workoutRemoveConfig));
        i.setClosed(true);
        i.setKeyboardType(FastKeyboardType.INLINE);
        i.setKeyboardButtons(new ArrayList<>());
    }

    @Override
    public void inlinePreparationLogic(WorkoutRemoveInquiry i, CallbackQuery cq) {
        if (isNullOrEmpty(i.getWorkoutName())) {
            i.setWorkoutName(cq.getData());
            i.setAnswerMessage("С какого дня начнем?");
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtons(DateTimeUtils.getDayOfWeek());
        }
    }
}