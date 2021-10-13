package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {
}
