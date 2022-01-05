package dev.chel_shev.nelly.youtube;

import com.goebl.david.Response;
import com.goebl.david.Webb;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class YouTubeApi {

    @Value("${youtube.api}")
    private  String DEVELOPER_KEY;
    @Value("${youtube.channel}")
    private  String DEVELOPER_CHANNEL;
    private static final String URL_SUBSCRIPTIONS = "https://www.googleapis.com/youtube/v3/subscriptions";
    private static final String URL_SEARCH = "https://www.googleapis.com/youtube/v3/search";

    public  List<VideoDTO> getLastVideos(String channelId) {
        Webb webb = Webb.create();
        Response<JSONObject> response = webb.get(URL_SEARCH)
                .params(getParamsSearch(channelId))
                .ensureSuccess()
                .asJsonObject();
        return createVideo(response.getBody());
    }

    public  List<SubscriptionDTO> getSubscriptions() throws JSONException {
        Webb webb = Webb.create();
        Response<JSONObject> response = webb.get(URL_SUBSCRIPTIONS)
                .params(getParamsSubscriptions())
                .ensureSuccess()
                .asJsonObject();
        return createSubscriptions(response.getBody());
    }

    private  Map<String, Object> getParamsSubscriptions() {
        return new HashMap<>() {{
            put("key", DEVELOPER_KEY);
            put("part", "snippet,contentDetails");
            put("maxResults", 50);
            put("channelId", DEVELOPER_CHANNEL);
        }};
    }

    private  Map<String, Object> getParamsSearch(String channelId) {
        return new HashMap<>() {{
            put("key", DEVELOPER_KEY);
            put("part", "id,snippet");
            put("maxResults", 5);
            put("order", "date");
            put("channelId", channelId);
        }};
    }

    private  List<VideoDTO> createVideo(JSONObject jsonVideos) {
        List<VideoDTO> videos = new ArrayList<>();
        JSONArray items = jsonVideos.getJSONArray("items");
        for (Object item : items) {
            VideoDTO video = getVideo((JSONObject) item);
            if (null != video)
                videos.add(video);
        }
        return videos;
    }

    public  List<SubscriptionDTO> createSubscriptions(JSONObject jsonChannel) throws JSONException {
        List<SubscriptionDTO> subscriptions = new ArrayList<>();
        JSONArray items = jsonChannel.getJSONArray("items");
        for (Object item : items) {
            SubscriptionDTO subscription = getSubscription((JSONObject) item);
            if (null != subscription)
                subscriptions.add(subscription);
        }
        return subscriptions;
    }

    private  VideoDTO getVideo(JSONObject item) throws JSONException {
        try {
            String videoId = item.getJSONObject("id").getString("videoId");
            String publishedAt = item.getJSONObject("snippet").getString("publishedAt");
            return new VideoDTO(videoId, ZonedDateTime.parse(publishedAt));
        } catch (Exception e) {
            log.info("ERROR item: " + item);
            return null;
        }
    }

    private  SubscriptionDTO getSubscription(JSONObject item) throws JSONException {
        try {
            String channelId = item.getJSONObject("snippet").getJSONObject("resourceId").getString("channelId");
            String title = item.getJSONObject("snippet").getString("title");
            Integer totalItemCount = item.getJSONObject("contentDetails").getInt("totalItemCount");
            return new SubscriptionDTO(channelId, title, totalItemCount);
        } catch (Exception e) {
            log.info("ERROR item: " + item);
            return null;
        }
    }
}
