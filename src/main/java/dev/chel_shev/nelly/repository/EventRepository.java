package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.inquiry.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Optional<EventEntity> findByCalendarUserAndAnswerMessageId(UserEntity user, Integer answerMessageId);
}

