package dev.chel_shev.fast.event.bday;

import dev.chel_shev.fast.entity.event.FastBdayEventEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.FastEventId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@FastEventId(command = "/bday")
public class BdayEvent extends FastEvent {
    private String name;
    private LocalDateTime date;

    public void init(FastEventEntity entity, FastUserSubscriptionEntity user) {
        super.init(entity, user);
        this.name = ((FastBdayEventEntity) entity).getName();
        this.date = ((FastBdayEventEntity) entity).getDate();
    }

    public FastEventEntity getEntity() {
        return new FastBdayEventEntity(this);
    }
}