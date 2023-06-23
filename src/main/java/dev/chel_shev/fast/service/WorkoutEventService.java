package dev.chel_shev.fast.service;

import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.event.workout.FastWorkoutEvent;
import dev.chel_shev.fast.inquiry.command.unknownUser.UnknownUserConfig;
import dev.chel_shev.fast.repository.FastEventRepository;
import dev.chel_shev.fast.repository.UserSubscriptionRepository;
import dev.chel_shev.fast.repository.event.WorkoutEventRepository;
import dev.chel_shev.fast.type.FastKeyboardType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WorkoutEventService extends FastEventServiceImpl<FastWorkoutEvent> {

    private final WorkoutEventRepository workoutEventRepository;

    public WorkoutEventService(FastEventRepository eventRepository, FastUserService userService,
                               FastAnswerService answerService, UnknownUserConfig unknownUserConfig,
                               WorkoutEventRepository workoutEventRepository, UserSubscriptionRepository subscriptionRepository) {
        super(eventRepository, userService, answerService, unknownUserConfig, subscriptionRepository);
        this.workoutEventRepository = workoutEventRepository;
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

    public Long save(FastWorkoutEvent event) {
        return workoutEventRepository.save(new FastWorkoutEventEntity(event)).getId();
    }
}