package dev.chel_shev.nelly.repository.workout;

import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {
}
