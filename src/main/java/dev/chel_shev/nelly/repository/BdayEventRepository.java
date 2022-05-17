package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface BdayEventRepository extends JpaRepository<BdayEventEntity, Long> {

    boolean existsByNameAndDate(String name, LocalDateTime date);

    boolean existsByName(String name);

    List<BdayEventEntity> findByName(String name);

    BdayEventEntity findByNameAndDate(String name, LocalDateTime date);

    List<BdayEventEntity> findByNameAndUser(String name, UserEntity user);

    boolean existsByNameAndDateAndUser(String name, LocalDateTime date, UserEntity user);
}
