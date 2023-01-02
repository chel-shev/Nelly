package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutEventRepository extends JpaRepository<WorkoutEventEntity, Long> {
    List<WorkoutEventEntity> findByUser(UserEntity user);
}
