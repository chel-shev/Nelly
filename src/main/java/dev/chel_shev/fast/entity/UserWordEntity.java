package dev.chel_shev.fast.entity;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.type.FastStudyTimelineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    private Integer exam;

    @ManyToOne
    private FastUserEntity user;

    @ManyToOne
    private WordEntity word;

    public UserWordEntity(LocalDateTime time, FastUserEntity user, WordEntity word) {
        this.time = time;
        this.user = user;
        this.word = word;
    }
}
