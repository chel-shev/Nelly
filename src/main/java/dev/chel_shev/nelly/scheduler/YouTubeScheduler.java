package dev.chel_shev.nelly.scheduler;

import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.YouTubeCashEntity;
import dev.chel_shev.nelly.repository.UserRepository;
import dev.chel_shev.nelly.repository.YouTubeCashRepository;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.DateTimeUtils;
import dev.chel_shev.nelly.youtube.SubscriptionDTO;
import dev.chel_shev.nelly.youtube.VideoDTO;
import dev.chel_shev.nelly.youtube.YouTubeApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class YouTubeScheduler {

    private final YouTubeCashRepository youTubeCashRepository;
    private final UserRepository userRepository;
    private final BotSender sender;
    private final YouTubeApi youTubeApi;
    private static final String URL_VIDEO = "https://www.youtube.com/watch?v=";

    @Scheduled(cron = DateTimeUtils.EVERY_MINUTE)
    public void schedule() {
        log.info("YouTubeScheduler is started!");
        Map<String, YouTubeCashEntity> subsCashed = youTubeCashRepository.findAll().stream().collect(Collectors.toMap(YouTubeCashEntity::getChannelId, Function.identity()));
        List<SubscriptionDTO> subs = youTubeApi.getSubscriptions();
        Optional<UserEntity> user = userRepository.findByUserName("chel_shev");
        subs.forEach(e -> {
            if (subsCashed.containsKey(e.getChannelId())) {
                YouTubeCashEntity youTubeCashEntity = subsCashed.get(e.getChannelId());
                if (!e.getTotalItemCount().equals(youTubeCashEntity.getTotalItemCount())) {
                    List<VideoDTO> lastVideos = youTubeApi.getLastVideos(e.getChannelId());
                    List<String> videoDTOS = lastVideos.stream()
                            .filter(v -> v.getPublishedAt().isAfter(youTubeCashEntity.getLastPublished()))
                            .map(v -> URL_VIDEO + v.getVideoId()).toList();
                    updateLastPublished(e, lastVideos.get(0));
                    videoDTOS.forEach(l -> sender.sendMessage(user.get(), KeyboardType.NONE, l));
                }
            } else {
                YouTubeCashEntity youTubeCashEntity = new YouTubeCashEntity(e.getChannelId(), e.getTitle(), e.getTotalItemCount(), ZonedDateTime.now().minusDays(2));
                youTubeCashRepository.save(youTubeCashEntity);
            }
        });
        log.info("YouTubeScheduler is finished!");
    }

    private void updateLastPublished(SubscriptionDTO sub, VideoDTO video) {
        YouTubeCashEntity youTubeCashEntity = youTubeCashRepository.findByChannelId(sub.getChannelId()).orElseGet(() -> new YouTubeCashEntity(sub.getChannelId(), sub.getTitle(), sub.getTotalItemCount(), video.getPublishedAt()));
        youTubeCashEntity.setLastPublished(video.getPublishedAt());
        youTubeCashEntity.setTotalItemCount(sub.getTotalItemCount());
        youTubeCashEntity.setTitle(sub.getTitle());
        youTubeCashRepository.save(youTubeCashEntity);
    }
}