package dev.chel_shev.fast.entity.event;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.WordEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.language.FastLanguageEvent;
import dev.chel_shev.fast.type.FastPeriodType;
import dev.chel_shev.fast.type.FastStudyTimeType;
import dev.chel_shev.fast.type.FastStudyTimelineType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "fast_event_word")
public class FastWordEventEntity extends FastEventEntity {

    @ManyToOne
    private WordEntity word;

    @Enumerated(EnumType.STRING)
    private FastStudyTimelineType timeline;

    @Enumerated(EnumType.STRING)
    private FastStudyTimeType time;

    public FastWordEventEntity(FastCommandEntity command, FastPeriodType periodType, LocalDateTime dateTime, FastUserSubscriptionEntity user, WordEntity word, FastStudyTimelineType timeline, FastStudyTimeType time) {
        super(command, periodType, dateTime, user);
        this.word = word;
        this.time = time;
        this.timeline = timeline;
    }

    public FastWordEventEntity(FastLanguageEvent e) {
        super(e);
        this.word = e.getWord();
        this.time = e.getTime();
        this.timeline = e.getTimeline();
    }

    public FastWordEventEntity() {

    }
}