package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Optional<EventEntity> findByCalendarUserAndAnswerMessageId(UserEntity user, Integer answerMessageId);
}

