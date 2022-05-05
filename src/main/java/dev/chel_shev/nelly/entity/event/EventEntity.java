package dev.chel_shev.nelly.entity.event;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.type.EventType;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "event")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private PeriodType periodType;
    private LocalDateTime eventDateTime;
    private Boolean closed = false;
    private String answerMessage;
    private Integer answerMessageId;
    @Enumerated(EnumType.STRING)
    private KeyboardType keyboardType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CalendarEntity calendar;

    EventEntity(EventType eventType, PeriodType periodType, LocalDateTime eventDateTime) {
        this.eventType = eventType;
        this.eventDateTime = eventDateTime;
        this.periodType = periodType;
    }

    public EventEntity(Event event) {
        this.id = event.getId();
        this.periodType = event.getPeriodType();
        this.eventDateTime = event.getEventDateTime();
        this.eventType = event.getEventType();
        this.closed = event.getClosed();
        this.answerMessage = event.getAnswerMessage();
        this.answerMessageId = event.getAnswerMessageId();
        this.keyboardType = event.getKeyboardType();
        this.calendar = event.getCalendar();
    }
}