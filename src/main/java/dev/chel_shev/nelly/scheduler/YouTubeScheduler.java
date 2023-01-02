package dev.chel_shev.nelly.scheduler;

import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.bot.Markdown;
import dev.chel_shev.nelly.entity.YouTubeCacheEntity;
import dev.chel_shev.nelly.entity.YouTubeEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.repository.UserRepository;
import dev.chel_shev.nelly.repository.YouTubeCacheRepository;
import dev.chel_shev.nelly.repository.YouTubeRepository;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.DateTimeUtils;
import dev.chel_shev.nelly.youtube.channel.PlaylistApi;
import dev.chel_shev.nelly.youtube.channel.VideoDTO;
import dev.chel_shev.nelly.youtube.subscriptions.SubscriptionDTO;
import dev.chel_shev.nelly.youtube.subscriptions.SubscriptionsApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class YouTubeScheduler {

    private final YouTubeRepository youTubeRepository;
    private final YouTubeCacheRepository youTubeCacheRepository;
    private final UserRepository userRepository;
    private final BotSender sender;
    private final PlaylistApi playlistApi;
    private final SubscriptionsApi subscriptionsApi;
    private static final String URL_VIDEO = "https://www.youtube.com/watch?v=";
    private static final String VERSION = " v.6";

    @Scheduled(cron = DateTimeUtils.EVERY_MINUTE)
    public void schedule() {
        log.debug("YouTubeScheduler" + VERSION + " is started!");
        Map<String, YouTubeEntity> subsCashed = youTubeRepository.findAll().stream().collect(Collectors.toMap(YouTubeEntity::getChannelId, Function.identity()));
        List<String> all = youTubeCacheRepository.findAll().stream().map(YouTubeCacheEntity::getVideoId).toList();
        List<SubscriptionDTO> subs = subscriptionsApi.getSubscriptions();
        Optional<UserEntity> user = userRepository.findByUserName("chel_shev");
        subs.forEach(e -> {
            if (subsCashed.containsKey(e.getChannelId())) {
                YouTubeEntity youTubeEntity = subsCashed.get(e.getChannelId());
                if (!e.getTotalItemCount().equals(youTubeEntity.getTotalItemCount())) {
                    log.info("ChannelId: " + e.getChannelId() + " | ItemCount: " + e.getTotalItemCount() + " | In the db: " + youTubeEntity.getTotalItemCount());
                    List<VideoDTO> lastVideos = playlistApi.getLastVideos(e.getPlaylistId()).stream().sorted(Comparator.comparing(VideoDTO::getPublishedAt, Comparator.nullsLast(Comparator.reverseOrder()))).toList();
                    StringBuilder videos = new StringBuilder();
                    ListIterator<String> indexes = List.of("1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣").listIterator();
                    lastVideos.stream()
                            .filter(v -> {
                                boolean after = !all.contains(v.getVideoId()) && v.getPublishedAt().isAfter(youTubeEntity.getLastPublished());
                                log.info("Video: " + v.getVideoId() + " | Published At: " + v.getPublishedAt() + " | Last send: " + youTubeEntity.getLastPublished() + " | " + (after ? " +" : " -"));
                                return after;
                            })
                            .forEach(v -> {
                                videos.append(Markdown.link(indexes.next(), URL_VIDEO + v.getVideoId())).append(", ");
                                youTubeCacheRepository.save(new YouTubeCacheEntity(v.getVideoId(), v.getPublishedAt()));
                            });
                    if (!videos.isEmpty())
                        sender.sendMessage(user.get(), KeyboardType.COMMON, e.getTitle() + ": " + videos.substring(0, videos.length() - 2), true);
                    updateLastPublished(e, lastVideos.get(0));
                }
            } else {
                YouTubeEntity youTubeEntity = new YouTubeEntity(e.getChannelId(), e.getPlaylistId(), e.getTitle(), e.getTotalItemCount(), ZonedDateTime.now().minusHours(2));
                youTubeRepository.save(youTubeEntity);
            }
        });
        log.debug("YouTubeScheduler" + VERSION + " is finished!");
    }

    private void updateLastPublished(SubscriptionDTO sub, VideoDTO video) {
        YouTubeEntity youTubeEntity = youTubeRepository.findByChannelId(sub.getChannelId()).orElseGet(() -> new YouTubeEntity(sub.getChannelId(), sub.getPlaylistId(), sub.getTitle(), sub.getTotalItemCount(), video.getPublishedAt()));
        youTubeEntity.setLastPublished(video.getPublishedAt());
        youTubeEntity.setTotalItemCount(sub.getTotalItemCount());
        youTubeEntity.setTitle(sub.getTitle());
        youTubeRepository.save(youTubeEntity);
    }
}