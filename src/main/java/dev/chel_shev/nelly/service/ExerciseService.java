package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutExercisesEntity;
import dev.chel_shev.nelly.repository.ExerciseRepository;
import dev.chel_shev.nelly.repository.WorkoutExercisesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final WorkoutExercisesRepository weR;
    private final ExerciseRepository exeR;

    public List<WorkoutExercisesEntity> getExerciseList(Long workoutId){
        return weR.findAllByWorkoutIdOrderByOrderExercise(workoutId);
    }

    public void save(ExerciseEntity exerciseEntity) {
        exeR.save(exerciseEntity);
    }
}