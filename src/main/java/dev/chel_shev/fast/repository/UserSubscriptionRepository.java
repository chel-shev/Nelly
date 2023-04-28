package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.type.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<FastUserSubscriptionEntity, Long> {

    List<FastUserSubscriptionEntity> findAllByFastUserChatId(String chadId);

    List<FastUserSubscriptionEntity> findAllByFastUser_ChatIdAndParentCommandAndType(String chadId, FastCommandEntity command, SubscriptionType type);

    FastUserSubscriptionEntity findByFastUser_ChatIdAndCommandAndTypeIn(String chadId, FastCommandEntity command, Collection<SubscriptionType> types);

    List<FastUserSubscriptionEntity> findAllByFastUserAndType(FastUserEntity user, SubscriptionType type);
}
