package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.entity.EventEntity;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {

    Optional<CalendarEntity> findByEventAndUser(EventEntity event, UserEntity user);

    boolean existsByEventAndUser(BdayEntity byNameAndDate, UserEntity user);
}