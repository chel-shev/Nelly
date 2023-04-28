package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FastEventRepository extends JpaRepository<FastEventEntity, Long> {

    List<FastEventEntity> findAllByUser_FastUserAndClosed(FastUserEntity user, boolean b);

    Optional<FastEventEntity> findByUser_FastUserAndAnswerMessageId(FastUserEntity fastUserEntity, Integer messageId);
}
