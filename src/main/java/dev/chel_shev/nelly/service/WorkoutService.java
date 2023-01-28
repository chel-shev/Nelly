package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.users.UserSubscriptionEntity;
import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutExerciseEntity;
import dev.chel_shev.nelly.repository.event.WorkoutEventRepository;
import dev.chel_shev.nelly.repository.event.workout.WorkoutRepository;
import dev.chel_shev.nelly.repository.user.UserSubscriptionRepository;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.chel_shev.nelly.type.EventType.WORKOUT;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository repository;
    private final WorkoutEventRepository eventRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final EventService<? extends Event> eventService;
    private final ExerciseService exerciseService;

    public List<String> getUnusedWorkout(UserEntity user) {
        Set<String> allWorkouts = repository.findAll().stream().map(WorkoutEntity::getName).collect(Collectors.toSet());
        Set<String> byUserWorkouts = eventService.getUserWorkouts(user).stream().map(e -> e.getWorkout().getName()).collect(Collectors.toSet());
        return allWorkouts.stream().filter(e -> !byUserWorkouts.contains(e)).toList();
    }

    public WorkoutEventEntity save(WorkoutEventEntity entity) {
        return eventRepository.save(entity);
    }

    public WorkoutEntity getByName(String workoutName) {
        return repository.findByName(workoutName);
    }

    public void initNextEvent(WorkoutEventEntity e) {
        LocalDateTime newEventDateTime = e.getEventDateTime().plus(e.getPeriodType().getAmount());
        e.setEventDateTime(newEventDateTime);
        if (e.getEventDateTime().isBefore(LocalDateTime.now()))
            return;
        e.setId(null);
        e.setAnswerMessageId(null);
        e.setStep(-1);
        e.setKeyboardType(KeyboardType.WORKOUT_PROCESS);
        save(e);
    }

    public void updateExercise(WorkoutEventEntity workoutEvent, String fileId) {
        List<WorkoutExerciseEntity> exerciseList = exerciseService.getExerciseList(workoutEvent.getWorkout().getId());
        ExerciseEntity exerciseEntity = exerciseList.get(workoutEvent.getStep()).getExercise();
        exerciseEntity.setFileId(fileId);
        exerciseService.save(exerciseEntity);
    }

    public void updateWorkout(WorkoutEventEntity workoutEvent, String fileId) {
        WorkoutEntity workout = workoutEvent.getWorkout();
        workout.setFileId(fileId);
        repository.save(workout);
    }

    public UserSubscriptionEntity getSubscription(UserEntity user) {
        return userSubscriptionRepository.findByUserAndSubscriptionEventType(user, WORKOUT);
    }
}
