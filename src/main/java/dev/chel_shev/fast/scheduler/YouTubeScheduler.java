package dev.chel_shev.fast.scheduler;

import dev.chel_shev.fast.FastMarkdown;
import dev.chel_shev.fast.FastSender;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.youtube.YoutubeEvent;
import dev.chel_shev.fast.inquiry.keyboard.common.CommonKeyboardInquiry;
import dev.chel_shev.fast.service.FastKeyboardService;
import dev.chel_shev.fast.service.FastUserService;
import dev.chel_shev.fast.type.FastKeyboardType;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class YouTubeScheduler {

    private final FastKeyboardService keyboardService;
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
        ZoneOffset zoneOffset = user.getZoneOffset();
        GregorianCalendar calendar = GregorianCalendar.from(LocalDateTime.now().atZone(zoneOffset));
        boolean mute = calendar.get(Calendar.HOUR_OF_DAY) > 0 && calendar.get(Calendar.HOUR_OF_DAY) < 8;
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
                                videos.append(FastMarkdown.link(indexes.next(), URL_VIDEO + v.getVideoId())).append(", ");
                                youTubeService.saveYoutubeCache(new YouTubeCacheEntity(v.getVideoId(), v.getPublishedAt()));
                            });
                    if (!videos.isEmpty()) {
//                        sender.sendMessage(user.getChatId(),e.getTitle() + ": " + videos.substring(0, videos.length() - 2), FastKeyboardType.REPLY, keyboardService.getButtons(CommonKeyboardInquiry.class), true);
                        FastEvent event = new YoutubeEvent();
                        sender.sendMessage(user.getChatId(), e.getTitle() + ": " + videos.substring(0, videos.length() - 2), FastKeyboardType.REPLY, keyboardService.getButtons(CommonKeyboardInquiry.class), true, mute);
                    }
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