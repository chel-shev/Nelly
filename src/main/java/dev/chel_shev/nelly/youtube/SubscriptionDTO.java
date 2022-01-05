package dev.chel_shev.nelly.youtube;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionDTO {

    String channelId;
    String title;
    Integer totalItemCount;
}
