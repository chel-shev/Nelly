package dev.chel_shev.fast.event.workout;

import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.event.FastEventHandler;
import dev.chel_shev.fast.service.WorkoutEventService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.nelly.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;

import static dev.chel_shev.nelly.type.KeyboardType.INLINE_NEXT;
import static dev.chel_shev.nelly.type.KeyboardType.INLINE_PREV;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class FastWorkoutHandler extends FastEventHandler<FastWorkoutEvent> {

    private final FastWorkoutConfig workoutConfig;
    private final WorkoutService workoutService;
    private final WorkoutEventService workoutEventService;

    @Override
    public void inlinePreparationLogic(FastWorkoutEvent e, CallbackQuery callbackQuery) {
        if (INLINE_NEXT.getLabel().equals(callbackQuery.getData())) {
            e.incStep();
            e.setKeyboardType(FastKeyboardType.INLINE);
            e.setKeyboardButtons(workoutEventService.getWorkoutProcess(e));
        } else if (INLINE_PREV.getLabel().equals(callbackQuery.getData())) {
            e.decStep();
            e.setKeyboardType(FastKeyboardType.INLINE);
            e.setKeyboardButtons(workoutEventService.getWorkoutProcess(e));
        } else {
            e.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, workoutConfig));
            e.setKeyboardType(FastKeyboardType.REPLY);
            e.setKeyboardButtons(new ArrayList<>());
            e.setClosed(true);
        }
    }

    @Override
    public FastWorkoutEvent updateEvent(FastWorkoutEvent e, Message reply) {
        if (isNull(reply)) return null;
        e.setAnswerMessageId(reply.getMessageId());
        if (!e.getClosed())
            workoutService.updateExercise(((FastWorkoutEventEntity) e.getEntity()).getWorkout(), e.getStep(), botResources.getPhoto(reply).getFileId());
        eventService.save(e);
        return e;
    }
}
