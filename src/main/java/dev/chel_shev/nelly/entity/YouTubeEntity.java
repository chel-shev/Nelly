package dev.chel_shev.nelly.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Setter
@Getter
@Entity
@Table(name = "youtube")
@NoArgsConstructor
public class YouTubeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String channelId;
    private String playlistId;
    private String title;
    private Integer totalItemCount;
    private ZonedDateTime lastPublished;

    public YouTubeEntity(String channelId, String playlistId, String title, Integer totalItemCount, ZonedDateTime lastPublished) {
        this.channelId = channelId;
        this.playlistId = playlistId;
        this.title = title;
        this.totalItemCount = totalItemCount;
        this.lastPublished = lastPublished;
    }
}
