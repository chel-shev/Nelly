package dev.chel_shev.nelly.youtube;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class YouTubeConfigurer {

    @Value("${youtube.api}")
    private String DEVELOPER_KEY;
    @Value("${youtube.channel}")
    private String DEVELOPER_CHANNEL;

    public Map<String, Object> getParamsSubscriptions() {
        return new HashMap<>() {{
            put("key", DEVELOPER_KEY);
            put("part", "snippet,contentDetails");
            put("maxResults", 50);
            put("channelId", DEVELOPER_CHANNEL);
        }};
    }

    public Map<String, Object> getParamsSearch(String channelId) {
        return new HashMap<>() {{
            put("key", DEVELOPER_KEY);
            put("part", "snippet");
            put("maxResults", 10);
            put("playlistId", channelId);
        }};
    }

    public Map<String, Object> getParamsChannel(String channelId) {
        return new HashMap<>() {{
            put("key", DEVELOPER_KEY);
            put("part", "contentDetails");
            put("id", channelId);
        }};
    }
}
