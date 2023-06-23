package dev.chel_shev.fast.inquiry;


import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.inquiry.FastCommonInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Slf4j(topic = "inquiry")
@NoArgsConstructor
public abstract class FastInquiry {

    private Long id;
    private String message;
    private Integer messageId;
    private String answerMessage;
    private Integer answerMessageId;
    private LocalDateTime date;
    private boolean closed;
    private FastUserEntity user;
    private FastCommandEntity command;
    private List<String> keyboardButtons;
    private FastKeyboardType keyboardType;

    public void init(FastInquiryEntity entity, FastUserEntity user) {
        this.id = entity.getId();
        this.date = entity.getDate();
        this.message = entity.getMessage();
        this.messageId = entity.getMessageId();
        this.answerMessage = entity.getAnswerMessage();
        this.answerMessageId = entity.getAnswerMessageId();
        this.keyboardType = entity.getKeyboardType();
        this.closed = entity.isClosed();
        this.command = entity.getCommand();
        this.user = user;
        log.info("INIT {}", this);
    }

    public void init(String message, Integer messageId, FastUserEntity userEntity, FastCommandEntity commandEntity) {
        this.message = message;
        this.messageId = messageId;
        this.date = LocalDateTime.now();
        this.user = userEntity;
        this.command = commandEntity;
        log.info("CREATE {}", this);
    }

    public void init(FastUserEntity userEntity, FastCommandEntity commandEntity) {
        this.date = LocalDateTime.now();
        this.user = userEntity;
        this.command = commandEntity;
        log.info("CREATE {}", this);
    }

    public String getArgFromMassage(String message, int index) {
        try {
            return message.split(" ")[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FastBotException("Неверное кол-во аргументов :(");
        }
    }

    public boolean isNotReadyForExecute() {
        return false;
    }

    public FastInquiryEntity getEntity() {
        return new FastCommonInquiryEntity(this);
    }

    public String toString() {
        return "Inquiry(id=" + this.getId() + ", message=" + this.getMessage() + ", messageId=" + this.getMessageId() + ", date=" + this.getDate() + ", closed=" + this.isClosed() + ", user=" + this.getUser() + ", command=" + this.getCommand() + ", answerMessage=" + this.getAnswerMessage() + ", answerMessageId=" + this.getAnswerMessageId() + ")";
    }
}