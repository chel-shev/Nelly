package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findAllByUserAndClosed(UserEntity userEntity, boolean closed);

    List<EventEntity> findAllByUser(UserEntity user);

    Optional<EventEntity> findByUserAndAnswerMessageId(UserEntity user, Integer messageId);
}