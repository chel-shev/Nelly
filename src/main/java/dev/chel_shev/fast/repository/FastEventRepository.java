package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FastEventRepository extends JpaRepository<FastEventEntity, Long> {

    List<FastEventEntity> findAllByUser_FastUserAndClosed(FastUserEntity user, boolean b);
}
