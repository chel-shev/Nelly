package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.entity.event.EventEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "calendar")
@NoArgsConstructor
public class CalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private EventEntity event;

    @ManyToOne
    private UserEntity user;

    public CalendarEntity(EventEntity event, UserEntity user) {
        this.event = event;
        this.user = user;
    }

    public ZonedDateTime getEventZonedDateTime() {
        return event.getEventDateTime().atZone(getUser().getZoneOffset());
    }
}