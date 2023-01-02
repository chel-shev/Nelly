package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutExercisesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutExercisesRepository extends JpaRepository<WorkoutExercisesEntity, Long> {
    List<WorkoutExercisesEntity> findAllByWorkoutIdOrderByOrderExercise(Long workoutId);
}
