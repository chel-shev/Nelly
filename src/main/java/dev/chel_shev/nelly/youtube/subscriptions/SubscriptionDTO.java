package dev.chel_shev.nelly.youtube.subscriptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionDTO {

    String channelId;
    String playlistId;
    String title;
    Integer totalItemCount;
}
