package dev.chel_shev.fast.entity.event;

import dev.chel_shev.fast.event.FastEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "fast_event_common")
@NoArgsConstructor
public class FastCommonEventEntity extends FastEventEntity {

    public FastCommonEventEntity(FastEvent event) {
        super(event);
    }
}
