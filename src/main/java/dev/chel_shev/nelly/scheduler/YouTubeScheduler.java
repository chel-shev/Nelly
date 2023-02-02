package dev.chel_shev.nelly.scheduler;

import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.event.youtube.YouTubeCacheEntity;
import dev.chel_shev.nelly.entity.event.youtube.YouTubeEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.service.YouTubeService;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class YouTubeScheduler {

    private final YouTubeService youTubeService;
    private final UserService userService;
    private final BotSender sender;
    private final PlaylistApi playlistApi;
    private final SubscriptionsApi subscriptionsApi;
    private static final String URL_VIDEO = "https://www.youtube.com/watch?v=";
    private static final String VERSION = " v.5";

    @Scheduled(cron = DateTimeUtils.EVERY_MINUTE)
    public void schedule() {
        log.debug("YouTubeScheduler" + VERSION + " is started!");
        Map<String, YouTubeEntity> subsCashed = youTubeService.getVideoId2Youtube();
        List<String> all = youTubeService.getAllVideoIds();
        List<SubscriptionDTO> subs = subscriptionsApi.getSubscriptions();
        UserEntity user = userService.getUserByName("chel_shev");
        subs.forEach(e -> {
            if (subsCashed.containsKey(e.getChannelId())) {
                YouTubeEntity youTubeEntity = subsCashed.get(e.getChannelId());
                if (!e.getTotalItemCount().equals(youTubeEntity.getTotalItemCount())) {
                    log.info("ChannelId: " + e.getChannelId() + " | ItemCount: " + e.getTotalItemCount() + " | In the db: " + youTubeEntity.getTotalItemCount());
                    List<VideoDTO> lastVideos = playlistApi.getLastVideos(e.getPlaylistId()).stream().sorted(Comparator.comparing(VideoDTO::getPublishedAt, Comparator.nullsLast(Comparator.reverseOrder()))).toList();
                    StringBuilder videos = new StringBuilder();
                    lastVideos.stream()
                            .filter(v -> {
                                boolean after = !all.contains(v.getVideoId()) && v.getPublishedAt().isAfter(youTubeEntity.getLastPublished());
                                log.info("Video: " + v.getVideoId() + " | Published At: " + v.getPublishedAt() + " | Last send: " + youTubeEntity.getLastPublished() + " | " + (after ? " +" : " -"));
                                return after;
                            })
                            .forEach(v -> {
                                videos.append(URL_VIDEO).append(v.getVideoId()).append("\n");
                                youTubeService.saveYoutubeCache(new YouTubeCacheEntity(v.getVideoId(), v.getPublishedAt()));
                            });
                    if (!videos.isEmpty())
                        sender.sendMessage(user, KeyboardType.COMMON, "▶️" + e.getTitle() + "\n" + videos, false);
                    youTubeService.updateLastPublished(e, lastVideos.get(0));
                }
            } else {
                YouTubeEntity youTubeEntity = new YouTubeEntity(e.getChannelId(), e.getPlaylistId(), e.getTitle(), e.getTotalItemCount(), ZonedDateTime.now().minusHours(2));
                youTubeService.saveYoutube(youTubeEntity);
            }
        });
        log.debug("YouTubeScheduler" + VERSION + " is finished!");
    }
}