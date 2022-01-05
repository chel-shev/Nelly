package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.YouTubeCashEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YouTubeCashRepository extends JpaRepository<YouTubeCashEntity, Long> {
    Optional<YouTubeCashEntity> findByChannelId(String channelId);
}
