package dev.chel_shev.nelly.youtube.channel;

import com.goebl.david.Response;
import com.goebl.david.Webb;
import dev.chel_shev.nelly.youtube.YouTubeConfigurer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelApi {

    private final YouTubeConfigurer configurer;

    private static final String URL_CHANNELS = "https://www.googleapis.com/youtube/v3/channels";
    private static final String QUOTA = "-1 Quota";

    public String getPlaylistIdByChannelId(String channelId) {
        Webb webb = Webb.create();
        log.debug("CHANNELS " + QUOTA);
        Response<JSONObject> channel = webb.get(URL_CHANNELS)
                .params(configurer.getParamsChannel(channelId))
                .ensureSuccess()
                .asJsonObject();
        return getPlaylistId(channel.getBody());
    }

    private String getPlaylistId(JSONObject jsonVideos) {
        JSONArray items = jsonVideos.getJSONArray("items");
        for (Object item : items) {
            try {
                return ((JSONObject) item).getJSONObject("contentDetails").getJSONObject("relatedPlaylists").getString("uploads");
            } catch (Exception ignored) {
            }
        }
        return "";
    }
}
