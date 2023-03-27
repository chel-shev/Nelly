package dev.chel_shev.nelly.repository.workout;

import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<WorkoutEntity, Long> {

    WorkoutEntity findByName(String workoutName);
}
