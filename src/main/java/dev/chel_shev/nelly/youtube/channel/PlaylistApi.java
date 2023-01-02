package dev.chel_shev.nelly.youtube.channel;

import com.goebl.david.Response;
import com.goebl.david.Webb;
import dev.chel_shev.nelly.youtube.channel.VideoDTO;
import dev.chel_shev.nelly.youtube.YouTubeConfigurer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaylistApi {

    private final YouTubeConfigurer configurer;

    private static final String URL_PLAYLIST_ITEMS = "https://youtube.googleapis.com/youtube/v3/playlistItems";
    private static final String QUOTA = "-1 Quota";

    public List<VideoDTO> getLastVideos(String playlistId) {
        Webb webb = Webb.create();
        log.debug("PLAYLIST_ITEMS " + QUOTA);
        Response<JSONObject> response = webb.get(URL_PLAYLIST_ITEMS)
                .params(configurer.getParamsSearch(playlistId))
                .ensureSuccess()
                .asJsonObject();
        return createVideo(response.getBody());
    }

    private List<VideoDTO> createVideo(JSONObject jsonVideos) {
        List<VideoDTO> videos = new ArrayList<>();
        JSONArray items = jsonVideos.getJSONArray("items");
        for (Object item : items) {
            VideoDTO video = getVideo((JSONObject) item);
            if (null != video)
                videos.add(video);
        }
        return videos;
    }

    private VideoDTO getVideo(JSONObject item) throws JSONException {
        try {
            String videoId = item.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
            String publishedAt = item.getJSONObject("snippet").getString("publishedAt");
            return new VideoDTO(videoId, ZonedDateTime.parse(publishedAt));
        } catch (Exception e) {
            log.info("ERROR item: " + item);
            return null;
        }
    }
}
