package dev.chel_shev.nelly.youtube.subscriptions;

import com.goebl.david.Response;
import com.goebl.david.Webb;
import dev.chel_shev.nelly.entity.youtube.YouTubeEntity;
import dev.chel_shev.nelly.repository.youtube.YouTubeRepository;
import dev.chel_shev.nelly.youtube.YouTubeConfigurer;
import dev.chel_shev.nelly.youtube.channel.ChannelApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionsApi {

    private final ChannelApi channelApi;
    private final YouTubeConfigurer configurer;
    private final YouTubeRepository youTubeCashRepository;


    private static final String URL_SUBSCRIPTIONS = "https://www.googleapis.com/youtube/v3/subscriptions";
    private static final String QUOTA = "-1 Quota";

    public List<SubscriptionDTO> getSubscriptions() throws JSONException {
        Map<String, YouTubeEntity> subsCashed = youTubeCashRepository.findAll().stream().collect(Collectors.toMap(YouTubeEntity::getChannelId, Function.identity()));

        Webb webb = Webb.create();
        log.debug("SUBSCRIPTIONS " + QUOTA);
        Response<JSONObject> subscriptionsJson = webb.get(URL_SUBSCRIPTIONS)
                .params(configurer.getParamsSubscriptions())
                .ensureSuccess()
                .asJsonObject();
        List<SubscriptionDTO> subscriptions = createSubscriptions(subscriptionsJson.getBody());
        return subscriptions.stream().peek(e -> {
            if (!subsCashed.containsKey(e.getChannelId()) || (subsCashed.containsKey(e.getChannelId()) && subsCashed.get(e.getChannelId()).getPlaylistId() == null))
                e.setPlaylistId(channelApi.getPlaylistIdByChannelId(e.getChannelId()));
            else
                e.setPlaylistId(subsCashed.get(e.getChannelId()).getPlaylistId());
        }).toList();
    }

    public List<SubscriptionDTO> createSubscriptions(JSONObject jsonChannel) throws JSONException {
        List<SubscriptionDTO> subscriptions = new ArrayList<>();
        JSONArray items = jsonChannel.getJSONArray("items");
        for (Object item : items) {
            SubscriptionDTO subscription = getSubscription((JSONObject) item);
            if (null != subscription)
                subscriptions.add(subscription);
        }
        return subscriptions;
    }

    private SubscriptionDTO getSubscription(JSONObject item) throws JSONException {
        try {
            String channelId = item.getJSONObject("snippet").getJSONObject("resourceId").getString("channelId");
            String title = item.getJSONObject("snippet").getString("title");
            Integer totalItemCount = item.getJSONObject("contentDetails").getInt("totalItemCount");
            return new SubscriptionDTO(channelId, null, title, totalItemCount);
        } catch (Exception e) {
            log.info("ERROR item: " + item);
            return null;
        }
    }
}
