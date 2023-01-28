package dev.chel_shev.nelly.bot.event;

import dev.chel_shev.nelly.entity.event.CommonEventEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.users.UserSubscriptionEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.type.EventType;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
public abstract class Event {

    private Long id;
    private PeriodType periodType;
    private LocalDateTime eventDateTime;
    private Boolean closed;
    private String answerMessage;
    private Integer answerMessageId;
    private KeyboardType keyboardType;
    private UserSubscriptionEntity userSubscription;

    public boolean isNotReadyForExecute() {
        return false;
    }

    public EventEntity getEntity() {
        return new CommonEventEntity(this);
    }

    public EventType getEventType() {
        try {
            return this.getClass().getAnnotation(EventId.class).value();
        } catch (Exception e) {
            throw new TelegramBotException("Event not defined!");
        }
    }

    public void init(EventEntity entity, UserSubscriptionEntity userSubscription) {
        this.id = entity.getId();
        this.periodType = entity.getPeriodType();
        this.eventDateTime = entity.getEventDateTime();
        this.closed = entity.getClosed();
        this.answerMessage = entity.getAnswerMessage();
        this.answerMessageId = entity.getAnswerMessageId();
        this.keyboardType = entity.getKeyboardType();
        this.userSubscription = userSubscription;
        log.info("INIT {}", this);
    }
}