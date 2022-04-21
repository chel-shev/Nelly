package dev.chel_shev.nelly.bot.event.workout;

import dev.chel_shev.nelly.bot.event.EventHandler;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.type.CommandLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.KeyboardType.*;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutHandler extends EventHandler<WorkoutEvent> {

    private final WorkoutConfig workoutConfig;

    @Override
    public void inlinePreparationLogic(WorkoutEvent e, CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals(INLINE_NEXT.label)) {
            e.incStep();
            e.setKeyboardType(WORKOUT_PROCESS);
        } else if (callbackQuery.getData().equals(INLINE_PREV.label)) {
            e.decStep();
            e.setKeyboardType(WORKOUT_PROCESS);
        } else {
            e.setAnswerMessage(aSer.generateAnswer(CommandLevel.FIRST, workoutConfig));
            e.setKeyboardType(COMMON);
            e.setClosed(true);
        }
    }

    @Override
    public EventEntity updateEvent(WorkoutEvent e, Message reply) {
        if (isNull(reply)) return null;
        e.setAnswerMessageId(reply.getMessageId());
        if (!e.getClosed())
            workoutService.updateExercise((WorkoutEventEntity) e.getEntity(), botResources.getPhoto(reply).getFileId());
        return save(e.getEntity());
    }
}
