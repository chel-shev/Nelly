package dev.chel_shev.nelly.repository.event.workout;


import dev.chel_shev.nelly.entity.workout.WorkoutExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutExercisesRepository extends JpaRepository<WorkoutExerciseEntity, Long> {
    List<WorkoutExerciseEntity> findAllByWorkoutIdOrderByOrderExercise(Long workoutId);
}
