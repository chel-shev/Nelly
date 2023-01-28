package dev.chel_shev.nelly.repository.user;

import dev.chel_shev.nelly.entity.users.SubscriptionEntity;
import dev.chel_shev.nelly.entity.users.UserSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
}
