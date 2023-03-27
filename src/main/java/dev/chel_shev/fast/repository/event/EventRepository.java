package dev.chel_shev.fast.repository.event;

import dev.chel_shev.fast.entity.event.FastEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<FastEventEntity, Long> {

//    List<FastEventEntity> findAllByUserSubscriptionUserAndClosed(UserEntity userEntity, boolean closed);
//
//    List<FastEventEntity> findAllByUserSubscriptionUser(UserEntity user);
//
//    Optional<FastEventEntity> findByUserSubscriptionUserAndAnswerMessageId(UserEntity user, Integer messageId);
}