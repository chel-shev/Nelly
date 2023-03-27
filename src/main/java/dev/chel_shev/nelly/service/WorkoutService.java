package dev.chel_shev.nelly.service;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.repository.UserSubscriptionRepository;
import dev.chel_shev.fast.service.FastCommandService;
import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutExerciseEntity;
import dev.chel_shev.nelly.repository.workout.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository repository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final ExerciseService exerciseService;
    private final FastCommandService commandService;

    public List<String> getAvailableWorkouts(String chatId) {
        Set<String> allWorkouts = repository.findAll().stream().map(WorkoutEntity::getName).collect(Collectors.toSet());
        FastCommandEntity command = commandService.getCommand("/workout");
        List<String> workouts = repository.findAll().stream().map(WorkoutEntity::getName).toList();

//        userSubscriptionRepository.findByFastUserAndCommandAndType()
//        Set<String> byUserWorkouts = getAvailableWorkouts(chatId).stream().map(WorkoutEntity::getName).collect(Collectors.toSet());
//        return allWorkouts.stream().filter(e -> !byUserWorkouts.contains(e)).toList();
        return workouts;
    }


    public WorkoutEntity getByName(String workoutName) {
        return repository.findByName(workoutName);
    }


    public void updateExercise(WorkoutEntity workoutEntity, Integer step, String fileId) {
        List<WorkoutExerciseEntity> exerciseList = exerciseService.getExerciseList(workoutEntity.getId());
        ExerciseEntity exerciseEntity = exerciseList.get(step).getExercise();
        exerciseEntity.setFileId(fileId);
        exerciseService.save(exerciseEntity);
    }

    public void updateWorkout(WorkoutEntity workoutEntity, String fileId) {
        workoutEntity.setFileId(fileId);
        repository.save(workoutEntity);
    }
}
