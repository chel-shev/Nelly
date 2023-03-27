package dev.chel_shev.fast.scheduler;

import dev.chel_shev.fast.FastBotMarkdown;
import dev.chel_shev.fast.FastSender;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.service.FastUserService;
import dev.chel_shev.nelly.entity.youtube.YouTubeCacheEntity;
import dev.chel_shev.nelly.entity.youtube.YouTubeEntity;
import dev.chel_shev.nelly.service.YouTubeService;
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
import java.util.ListIterator;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class YouTubeScheduler {

    private final YouTubeService youTubeService;
    private final FastUserService userService;
    private final FastSender sender;
    private final PlaylistApi playlistApi;
    private final SubscriptionsApi subscriptionsApi;
    private static final String URL_VIDEO = "https://www.youtube.com/watch?v=";
    private static final String VERSION = " v.6";

    @Scheduled(cron = DateTimeUtils.EVERY_MINUTE)
    public void schedule() {
        log.debug("YouTubeScheduler" + VERSION + " is started!");
        Map<String, YouTubeEntity> subsCashed = youTubeService.getVideoId2Youtube();
        List<String> all = youTubeService.getAllVideoIds();
        List<SubscriptionDTO> subs = subscriptionsApi.getSubscriptions();
        FastUserEntity user = userService.getFastUserByName("chel_shev");
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
                                videos.append(FastBotMarkdown.link(indexes.next(), URL_VIDEO + v.getVideoId())).append(", ");
                                youTubeService.saveYoutubeCache(new YouTubeCacheEntity(v.getVideoId(), v.getPublishedAt()));
                            });
                    if (!videos.isEmpty())
                        sender.sendMessage(user.getChatId(),e.getTitle() + ": " + videos.substring(0, videos.length() - 2), true);
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