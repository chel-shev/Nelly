package dev.chel_shev.nelly.youtube.channel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class VideoDTO {

    String videoId;
    ZonedDateTime publishedAt;
}
