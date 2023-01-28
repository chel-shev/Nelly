package dev.chel_shev.nelly.repository.user;

import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.users.UserSubscriptionEntity;
import dev.chel_shev.nelly.type.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionEntity, Long> {

    UserSubscriptionEntity findByUserChatId(Long chadId);

    UserSubscriptionEntity findByUserAndSubscriptionEventType(UserEntity user, EventType eventType);

    List<UserSubscriptionEntity> findByUser(UserEntity user);
}
