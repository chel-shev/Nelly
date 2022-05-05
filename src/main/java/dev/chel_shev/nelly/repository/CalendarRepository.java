package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {

    Optional<CalendarEntity> findByEventAndUser(EventEntity event, UserEntity user);

    List<CalendarEntity> findAllByUserAndEvent_Closed(UserEntity user, Boolean closed);

    boolean existsByEventAndUser(EventEntity event, UserEntity user);

    List<CalendarEntity> findAllByUser(UserEntity user);
}
