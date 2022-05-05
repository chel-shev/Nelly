package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.repository.ExerciseRepository;
import dev.chel_shev.nelly.repository.WorkoutEventRepository;
import dev.chel_shev.nelly.repository.WorkoutRepository;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository repository;
    private final WorkoutEventRepository eventRepository;
    private final CalendarService calendarService;
    private final ExerciseRepository exerciseRepository;

    public List<String> getUnusedWorkout(UserEntity user) {
        List<String> allWorkouts = repository.findAll().stream().map(WorkoutEntity::getName).toList();
        List<String> byUserWorkouts = calendarService.getUserWorkout(user).stream().map(e -> e.getWorkout().getName()).toList();
        return allWorkouts.stream().filter(e -> !byUserWorkouts.contains(e)).toList();
    }

    public WorkoutEventEntity save(WorkoutEventEntity entity) {
        return eventRepository.save(entity);
    }

    public WorkoutEntity getByName(String workoutName) {
        return repository.findByName(workoutName);
    }

    public void initNextEvent(WorkoutEventEntity e, UserEntity user) {
        LocalDateTime eventDateTime = e.getEventDateTime();
        switch (e.getPeriodType()) {
            case ONCE -> e.setEventDateTime(eventDateTime.withYear(1970));
            case EVERY_ONE -> e.setEventDateTime(eventDateTime.plusDays(1));
            case EVERY_TWO -> e.setEventDateTime(eventDateTime.plusDays(2));
            case EVERY_THREE -> e.setEventDateTime(eventDateTime.plusDays(3));
            case EVERY_FOUR -> e.setEventDateTime(eventDateTime.plusDays(4));
            case EVERY_WEEK -> e.setEventDateTime(eventDateTime.plusDays(7));
            case EVERY_YEAR -> e.setEventDateTime(eventDateTime.plusYears(1));
            case EVERY_MOUTH -> e.setEventDateTime(eventDateTime.plusMonths(1));
        }
        if (e.getEventDateTime().isBefore(LocalDateTime.now()))
            return;
        e.setId(null);
        e.setAnswerMessageId(null);
        e.setStep(-1);
        e.setKeyboardType(KeyboardType.WORKOUT_PROCESS);
        WorkoutEventEntity workoutEvent = save(e);
        CalendarEntity calendarEntity = calendarService.addEvent(workoutEvent, user);
        workoutEvent.setCalendar(calendarEntity);
        save(workoutEvent);
    }

    public void updateExercise(WorkoutEventEntity workoutEvent, String fileId) {
        ExerciseEntity exerciseEntity = workoutEvent.getWorkout().getExercises().get(workoutEvent.getStep()).getExercise();
        exerciseEntity.setFileId(fileId);
        exerciseRepository.save(exerciseEntity);
    }

    public void updateWorkout(WorkoutEventEntity workoutEvent, String fileId) {
        WorkoutEntity workout = workoutEvent.getWorkout();
        workout.setFileId(fileId);
        repository.save(workout);
    }
}
