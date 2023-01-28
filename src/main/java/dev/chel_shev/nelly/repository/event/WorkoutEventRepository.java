package dev.chel_shev.nelly.repository.event;

import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutEventRepository extends JpaRepository<WorkoutEventEntity, Long> {
    List<WorkoutEventEntity> findByUserSubscriptionUser(UserEntity user);
}
