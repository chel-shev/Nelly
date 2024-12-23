package dev.chel_shev.fast.event.workout;

import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.event.FastEventHandler;
import dev.chel_shev.fast.inquiry.keyboard.common.CommonKeyboardInquiry;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import static dev.chel_shev.nelly.type.KeyboardType.*;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class FastWorkoutHandler extends FastEventHandler<FastWorkoutEvent> {

    private final FastWorkoutConfig workoutConfig;
    private final WorkoutService workoutService;

    @Override
    public void inlineExecutionLogic(FastWorkoutEvent e, CallbackQuery callbackQuery) {
        e.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, workoutConfig));
        e.setKeyboardType(FastKeyboardType.REPLY);
        e.setKeyboardButtons(keyboardService.getButton(CommonKeyboardInquiry.class));
        e.setClosed(true);
    }

    @Override
    public void inlinePreparationLogic(FastWorkoutEvent e, CallbackQuery callbackQuery) {
        if (INLINE_UP.getLabel().equals(callbackQuery.getData())) {
            e.incLevel();
            e.incStep();
        } else if (INLINE_DOWN.getLabel().equals(callbackQuery.getData())) {
            e.decLevel();
            e.incStep();
        } else if (Arrays.asList(INLINE_NEXT.getLabel(), INLINE_START.getLabel()).contains(callbackQuery.getData())) {
            e.incStep();
            setWorkoutInfo(e);
        } else if (INLINE_PREV.getLabel().equals(callbackQuery.getData())) {
            e.decStep();
            setWorkoutInfo(e);
        }
    }

    @Override
    public FastWorkoutEvent updateEvent(FastWorkoutEvent e, Message reply) {
        if (isNull(reply)) return null;
        e.setAnswerMessageId(reply.getMessageId());
        if (!e.getClosed() && !(e.getWorkout().isProgressable() && e.getWorkout().getCountExercise().equals(e.getStep())))
            workoutService.updateExercise(((FastWorkoutEventEntity) e.getEntity()).getWorkout(), e.getStep(), botResources.getPhoto(reply).getFileId());
        eventService.save(e);
        return e;
    }

    private void setWorkoutInfo(FastWorkoutEvent e) {
        e.setKeyboardType(FastKeyboardType.INLINE);
        e.setKeyboardButtons(workoutService.getWorkoutProcess(e));
        if (e.getWorkout().isProgressable() && e.getWorkout().getCountExercise().equals(e.getStep())) {
            e.setAnswerMessage("Было легко? Усложним?");
            e.setFile(null);
        } else {
            ExerciseEntity exercise = workoutService.getExercise(e.getWorkout().getId(), e.getStep());
            e.setAnswerMessage(workoutService.getWorkoutTitle(exercise, e.getWorkout().getCountExercise(), e.getStep(), e.getLevel()));
            if (null != exercise.getFileId())
                e.setFile(new InputFile(exercise.getFileId()));
            else
                e.setFile(new InputFile(new ByteArrayInputStream(exercise.getImage()), exercise.getName()));
        }
    }
}
