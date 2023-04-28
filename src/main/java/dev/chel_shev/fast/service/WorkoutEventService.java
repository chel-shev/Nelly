package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.event.FastEventFactory;
import dev.chel_shev.fast.event.bday.BdayEvent;
import dev.chel_shev.fast.event.workout.FastWorkoutEvent;
import dev.chel_shev.fast.inquiry.command.unknownUser.UnknownUserConfig;
import dev.chel_shev.fast.repository.FastEventRepository;
import dev.chel_shev.fast.repository.event.WorkoutEventRepository;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.repository.UserSubscriptionRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dev.chel_shev.nelly.type.KeyboardType.*;
import static dev.chel_shev.nelly.type.KeyboardType.INLINE_DONE;

@Service
public class WorkoutEventService extends FastCommonEventService<BdayEvent> {

    private final WorkoutEventRepository eventRepository;

    public WorkoutEventService(FastEventRepository repository, FastUserService userService, FastAnswerService answerService, UnknownUserConfig unknownUserConfig, FastEventFactory eventFactory, WorkoutEventRepository eventRepository, UserSubscriptionRepository userSubscriptionRepository, UserSubscriptionRepository subscriptionRepository) {
        super(repository, userService, answerService, unknownUserConfig, eventFactory, subscriptionRepository);
        this.eventRepository = eventRepository;
    }

    public void initNextEvent(FastWorkoutEvent event) {
        LocalDateTime newEventDateTime = event.getDateTime().plus(event.getPeriodType().getAmount());
        event.setDateTime(newEventDateTime);
        if (event.getDateTime().isBefore(LocalDateTime.now()))
            return;
        event.setId(null);
        event.setAnswerMessageId(null);
        event.setStep(-1);
        event.setKeyboardType(FastKeyboardType.REPLY);
        save(event);
    }

    public FastWorkoutEventEntity save(FastWorkoutEvent event) {
        return eventRepository.save(new FastWorkoutEventEntity(event));
    }

    public List<String> getWorkoutProcess(FastWorkoutEvent event) {
        List<String> buttons = new ArrayList<>();
        int amountExercises = event.getWorkout().getCountExercise();
        int step = event.getStep();
        buttons.add(INLINE_CANCEL.label);
        if (amountExercises > 0 && step == -1) {
            buttons.add(INLINE_START.label);
        } else if (amountExercises > step + 1 && step > 0) {
            buttons.add(INLINE_PREV.label);
            buttons.add(INLINE_NEXT.label);
        } else if (amountExercises > step + 1) {
            buttons.add(INLINE_NEXT.label);
        } else {
            buttons.add(INLINE_PREV.label);
            buttons.add(INLINE_DONE.label);
        }
        return buttons;
    }
}
