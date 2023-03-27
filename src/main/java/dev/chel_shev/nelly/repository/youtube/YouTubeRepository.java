package dev.chel_shev.nelly.repository.youtube;

import dev.chel_shev.nelly.entity.youtube.YouTubeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YouTubeRepository extends JpaRepository<YouTubeEntity, Long> {
    Optional<YouTubeEntity> findByChannelId(String channelId);
}
