package dev.chel_shev.nelly.bot.event.bday;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.event.EventId;
import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.type.EventType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@EventId(EventType.BDAY)
public class BdayEvent extends Event {
    private String name;
    private LocalDateTime date;

    public EventEntity getEntity() {
        return new BdayEventEntity(this);
    }
}