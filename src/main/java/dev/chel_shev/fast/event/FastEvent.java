package dev.chel_shev.fast.event;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.event.FastCommonEventEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.type.FastPeriodType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Slf4j(topic = "event")
public abstract class FastEvent {

    private Long id;
    private String answerMessage;
    private Integer answerMessageId;
    private LocalDateTime dateTime;
    private Boolean closed;
    private List<String> keyboardButtons;
    private FastKeyboardType keyboardType;
    private FastPeriodType periodType;
    private FastUserSubscriptionEntity userSubscription;
    private FastCommandEntity command;

    private InputFile file;

    public boolean isNotReadyForExecute() {
        return false;
    }

    public FastEventEntity getEntity() {
        return new FastCommonEventEntity(this);
    }

    public void init(FastEventEntity entity, FastUserSubscriptionEntity user) {
        this.id = entity.getId();
        this.periodType = entity.getPeriodType();
        this.dateTime = entity.getDateTime();
        this.closed = entity.getClosed();
        this.answerMessage = entity.getAnswerMessage();
        this.answerMessageId = entity.getAnswerMessageId();
        this.keyboardType = entity.getKeyboardType();
        this.userSubscription = user;
        this.command = entity.getCommand();
        log.info("INIT {}", this);
    }

    public boolean hasMedia() {
        return null != file;
    }

    @Override
    public String toString() {
        return "FastEvent{" +
                "id=" + id +
                ", am=" + answerMessage +
                ", amId=" + answerMessageId +
                ", date=" + dateTime +
                ", closed=" + closed +
                ", kbButtons=" + keyboardButtons +
                ", kbType=" + keyboardType +
                ", periodType=" + periodType +
                ", user=" + userSubscription.getFastUser().getUserName() +
                ", command=" + command.getName() +
                ", pCommand=" + userSubscription.getParentCommand().getName();
    }
}