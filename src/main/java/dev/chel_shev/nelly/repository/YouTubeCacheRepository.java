package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.YouTubeCacheEntity;
import dev.chel_shev.nelly.entity.YouTubeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YouTubeCacheRepository extends JpaRepository<YouTubeCacheEntity, Long> {
}
