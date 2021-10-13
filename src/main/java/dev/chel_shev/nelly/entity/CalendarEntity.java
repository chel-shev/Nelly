package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.type.EventType;
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

    private LocalDateTime eventDateTime;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @OneToOne
    private EventEntity event;

    @ManyToOne
    private UserEntity user;

    public CalendarEntity(LocalDateTime eventDateTime, EventEntity event, EventType type, UserEntity user) {
        this.eventDateTime = eventDateTime;
        this.event = event;
        this.type = type;
        this.user = user;
    }

    public ZonedDateTime getEventZonedDateTime() {
        return getEventDateTime().atZone(getUser().getZoneOffset());
    }

    public LocalDateTime getEventDateTime() {
        if (type == EventType.EVERY_YEAR)
            return eventDateTime.withYear(LocalDateTime.now().getYear());
        else
            return eventDateTime;
    }
}