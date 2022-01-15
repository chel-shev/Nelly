package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.workout.UserWorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserWorkoutRepository extends JpaRepository<UserWorkoutEntity, Long> {

    Collection<UserWorkoutEntity> findByUserChatId(Long chatId);

    List<UserWorkoutEntity> findByUserId(Long Id);

    List<UserWorkoutEntity> findByUser(UserEntity user);
}
