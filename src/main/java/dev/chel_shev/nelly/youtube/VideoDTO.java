package dev.chel_shev.nelly.youtube;

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
