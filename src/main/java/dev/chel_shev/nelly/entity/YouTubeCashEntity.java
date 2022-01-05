package dev.chel_shev.nelly.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Setter
@Getter
@Entity
@Table(name = "youtube_cash")
@NoArgsConstructor
public class YouTubeCashEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String channelId;
    private String title;
    private Integer totalItemCount;
    private ZonedDateTime lastPublished;

    public YouTubeCashEntity(String channelId, String title, Integer totalItemCount, ZonedDateTime lastPublished) {
        this.channelId = channelId;
        this.title = title;
        this.totalItemCount = totalItemCount;
        this.lastPublished = lastPublished;
    }
}
