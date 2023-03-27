package dev.chel_shev.fast.repository.event;

import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutEventRepository extends JpaRepository<FastWorkoutEventEntity, Long> {
//    List<FastWorkoutEventEntity> findByUserSubscriptionUser(UserEntity user);
}
