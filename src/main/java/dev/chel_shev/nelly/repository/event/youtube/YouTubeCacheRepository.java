package dev.chel_shev.nelly.repository.event.youtube;

import dev.chel_shev.nelly.entity.event.youtube.YouTubeCacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YouTubeCacheRepository extends JpaRepository<YouTubeCacheEntity, Long> {
}
