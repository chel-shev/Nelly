package dev.chel_shev.fast.event.language.exam;

import dev.chel_shev.fast.entity.WordEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.event.FastWordEventEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.FastEventId;
import dev.chel_shev.fast.type.FastStudyTimeType;
import dev.chel_shev.fast.type.FastStudyTimelineType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@FastEventId(command = "/exam")
public class FastExamEvent extends FastEvent {

    private WordEntity word;
    private FastStudyTimeType time;
    private FastStudyTimelineType timeline;
    private boolean unknownWord = false;

    @Override
    public void init(FastEventEntity event, FastUserSubscriptionEntity user) {
        super.init(event, user);
        this.word = ((FastWordEventEntity) event).getWord();
        this.time = ((FastWordEventEntity) event).getTime();
        this.timeline = ((FastWordEventEntity) event).getTimeline();
    }

//    public FastEventEntity getEntity() {
//        return new FastWordEventEntity(this);
//    }

    @Override
    public boolean isNotReadyForExecute() {
        return !unknownWord;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", word=" + word +
                ", time=" + time +
                ", timeline=" + timeline +
                ", unknownWord=" + unknownWord +
                '}';
    }
}
