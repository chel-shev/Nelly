package dev.chel_shev.nelly.entity.event;

import dev.chel_shev.nelly.bot.event.bday.BdayEvent;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.users.UserSubscriptionEntity;
import dev.chel_shev.nelly.type.EventType;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "event_bday")
@NoArgsConstructor
public class BdayEventEntity extends EventEntity {

    private String name;
    private LocalDateTime date;

    public BdayEventEntity(String name, LocalDateTime date, LocalDateTime eventDateTime, UserSubscriptionEntity userSubscription) {
        super(EventType.BDAY, PeriodType.EVERY_YEAR, eventDateTime, userSubscription);
        this.name = name;
        this.date = date;
    }

    public BdayEventEntity(BdayEvent event) {
        super(event);
        this.name = event.getName();
        this.date = event.getDate();
    }
}
