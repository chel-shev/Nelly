package dev.chel_shev.nelly.repository.youtube;

import dev.chel_shev.nelly.entity.youtube.YouTubeCacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YouTubeCacheRepository extends JpaRepository<YouTubeCacheEntity, Long> {
}
