package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<FastUserSubscriptionEntity, Long> {

    List<FastUserSubscriptionEntity> findAllByFastUserChatId(String chadId);

    FastUserSubscriptionEntity findByFastUserAndCommand(FastUserEntity user, FastCommandEntity command);

    List<FastUserSubscriptionEntity> findAllByFastUser(FastUserEntity user);
}
