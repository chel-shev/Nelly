package dev.chel_shev.fast.entity.event;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.type.FastPeriodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "fast_event")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class FastEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private FastPeriodType periodType;
    private LocalDateTime dateTime;
    private Boolean closed = false;
    private String answerMessage;
    private Integer answerMessageId;
    @ManyToOne
    private FastCommandEntity command;
    @Enumerated(EnumType.STRING)
    private FastKeyboardType keyboardType;
    @ManyToOne
    private FastUserSubscriptionEntity user;

    FastEventEntity(FastCommandEntity command, FastPeriodType periodType, LocalDateTime dateTime, FastUserSubscriptionEntity user) {
        this.command = command;
        this.dateTime = dateTime;
        this.periodType = periodType;
        this.user = user;
    }

    public FastEventEntity(FastEvent event) {
        this.id = event.getId();
        this.periodType = event.getPeriodType();
        this.dateTime = event.getDateTime();
        this.command = event.getCommand();
        this.closed = event.getClosed();
        this.answerMessage = event.getAnswerMessage();
        this.answerMessageId = event.getAnswerMessageId();
        this.keyboardType = event.getKeyboardType();
        this.user = event.getUserSubscription();
    }
}