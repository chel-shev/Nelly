package dev.chel_shev.fast.entity.event;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.bday.BdayEvent;
import dev.chel_shev.fast.type.FastPeriodType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "fast_event_bday")
@NoArgsConstructor
public class FastBdayEventEntity extends FastEventEntity {

    private String name;
    private LocalDateTime date;

    public FastBdayEventEntity(String name, LocalDateTime date, FastCommandEntity command, LocalDateTime eventDateTime, FastUserSubscriptionEntity user) {
        super(command, FastPeriodType.EVERY_YEAR, eventDateTime, user);
        this.name = name;
        this.date = date;
    }

    public FastBdayEventEntity(BdayEvent event) {
        super(event);
        this.name = event.getName();
        this.date = event.getDateTime();
    }
}
