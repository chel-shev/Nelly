package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.event.youtube.YouTubeCacheEntity;
import dev.chel_shev.nelly.entity.event.youtube.YouTubeEntity;
import dev.chel_shev.nelly.repository.event.youtube.YouTubeCacheRepository;
import dev.chel_shev.nelly.repository.event.youtube.YouTubeRepository;
import dev.chel_shev.nelly.youtube.channel.VideoDTO;
import dev.chel_shev.nelly.youtube.subscriptions.SubscriptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YouTubeService {

    private final YouTubeRepository repository;
    private final YouTubeCacheRepository youTubeCacheRepository;

    public List<String> getAllVideoIds(){
        return youTubeCacheRepository.findAll().stream().map(YouTubeCacheEntity::getVideoId).toList();
    }

    public Map<String, YouTubeEntity> getVideoId2Youtube() {
        return repository.findAll().stream().collect(Collectors.toMap(YouTubeEntity::getChannelId, Function.identity()));
    }

    public void saveYoutubeCache(YouTubeCacheEntity youTubeCache){
        youTubeCacheRepository.save(youTubeCache);
    }

    public void updateLastPublished(SubscriptionDTO sub, VideoDTO video) {
        YouTubeEntity youTubeEntity = repository.findByChannelId(sub.getChannelId()).orElseGet(() -> new YouTubeEntity(sub.getChannelId(), sub.getPlaylistId(), sub.getTitle(), sub.getTotalItemCount(), video.getPublishedAt()));
        youTubeEntity.setLastPublished(video.getPublishedAt());
        youTubeEntity.setTotalItemCount(sub.getTotalItemCount());
        youTubeEntity.setTitle(sub.getTitle());
        repository.save(youTubeEntity);
    }

    public void saveYoutube(YouTubeEntity youTubeEntity) {
        repository.save(youTubeEntity);
    }
}

