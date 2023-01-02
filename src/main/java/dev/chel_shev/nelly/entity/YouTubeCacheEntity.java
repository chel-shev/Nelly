package dev.chel_shev.nelly.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Setter
@Getter
@Entity
@Table(name = "youtube_cache")
@NoArgsConstructor
public class YouTubeCacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoId;
    private ZonedDateTime lastPublished;

    public YouTubeCacheEntity(String videoId, ZonedDateTime lastPublished) {
        this.videoId = videoId;
        this.lastPublished = lastPublished;
    }
}
