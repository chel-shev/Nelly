package dev.chel_shev.nelly.repository.event;

import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findAllByUserSubscriptionUserAndClosed(UserEntity userEntity, boolean closed);

    List<EventEntity> findAllByUserSubscriptionUser(UserEntity user);

    Optional<EventEntity> findByUserSubscriptionUserAndAnswerMessageId(UserEntity user, Integer messageId);
}