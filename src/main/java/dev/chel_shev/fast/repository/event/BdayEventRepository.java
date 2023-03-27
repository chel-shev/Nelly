package dev.chel_shev.fast.repository.event;

import dev.chel_shev.fast.entity.event.FastBdayEventEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BdayEventRepository extends JpaRepository<FastBdayEventEntity, Long> {

    boolean existsByNameAndDate(String name, LocalDateTime date);

    boolean existsByName(String name);

    List<FastBdayEventEntity> findByName(String name);

    FastBdayEventEntity findByNameAndDate(String name, LocalDateTime date);

    FastBdayEventEntity findByNameAndUser_FastUser(String name, FastUserEntity userEntity);

    List<FastBdayEventEntity> findAllByUser_FastUser(FastUserEntity userEntity);

//    List<FastBdayEventEntity> findByNameAndUserSubscriptionUser(String name, UserEntity user);

//    boolean existsByNameAndDateAndUserSubscriptionUser(String name, LocalDateTime date, UserEntity user);
}
