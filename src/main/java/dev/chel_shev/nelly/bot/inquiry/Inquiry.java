package dev.chel_shev.nelly.bot.inquiry;


import dev.chel_shev.nelly.entity.CommandEntity;
import dev.chel_shev.nelly.entity.inquiry.CommonInquiryEntity;
import dev.chel_shev.nelly.entity.inquiry.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.bot.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
public abstract class Inquiry {

    private Long id;
    private String message;
    private Integer messageId;
    private LocalDateTime date;
    private boolean closed = false;

    private UserEntity user;
    private CommandEntity command;
    private String answerMessage;
    private Integer answerMessageId;
    private KeyboardType keyboardType;

    public void init(InquiryEntity entity, UserEntity user) {
        this.id = entity.getId();
        this.date = entity.getDate();
        this.message = entity.getMessage();
        this.messageId = entity.getMessageId();
        this.answerMessage = entity.getAnswerMessage();
        this.answerMessageId = entity.getAnswerMessageId();

        this.keyboardType = entity.getKeyboardType();
        this.command = entity.getCommand();
        this.closed = entity.isClosed();
        this.user = user;
        log.info("INIT {}", this);
    }

    public void init(String message, Integer messageId, UserEntity user, CommandEntity command) {
        this.message = message;
        this.messageId = messageId;
        this.date = LocalDateTime.now();
        this.user = user;
        this.command = command;
        log.info("CREATE {}", this);
    }

    public InquiryType getType() {
        try {
            return this.getClass().getAnnotation(InquiryId.class).value();
        } catch (Exception e) {
            throw new TelegramBotException("Inquiry not defined!");
        }
    }

    public boolean isNotReadyForExecute() {
        return false;
    }

    public String getArgFromMassage(int index) {
        try {
            return message.split(" ")[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TelegramBotException("Неверное кол-во аргументов :(");
        }
    }

    public InquiryEntity getEntity() {
        return new CommonInquiryEntity(this);
    }

    public String toString() {
        return "Inquiry(id=" + this.getId() + ", message=" + this.getMessage() + ", messageId=" + this.getMessageId() + ", date=" + this.getDate() + ", closed=" + this.isClosed() + ", user=" + this.getUser().getUserName() + ", command=" + this.getCommand() + ", answerMessage=" + this.getAnswerMessage() + ", answerMessageId=" + this.getAnswerMessageId() + ")";
    }
}