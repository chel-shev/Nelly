package dev.chel_shev.nelly.inquiry.prototype;


import dev.chel_shev.nelly.entity.CommandEntity;
import dev.chel_shev.nelly.entity.CommonInquiryEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.CommandService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@Slf4j
public abstract class Inquiry {

    private Long id;
    private String message;
    private LocalDateTime date;
    private boolean closed = false;

    private Map<CommandLevel, Set<String>> answer = new HashMap<>();

    private UserEntity user;
    private CommandEntity command;
    private String answerMessage;
    private KeyboardType keyboardType;

    public void generate(InquiryEntity entity, UserEntity user) {
        this.message = entity.getMessage();
        this.closed = entity.isClosed();
        this.date = entity.getDate();
        this.answerMessage = entity.getAnswerMessage();
        this.keyboardType = entity.getKeyboardType();
        this.command = entity.getCommand();
        this.user = user;
        log.info("INIT Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    public void generate(String message, UserEntity user, CommandEntity command) {
        this.message = message;
        this.date = LocalDateTime.now();
        this.user = user;
        this.command = command;
        log.info("CREATE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    public String getCommandName() {
        return this.getClass().getAnnotation(InquiryId.class).command();
    }

    @Autowired
    public void registerCommand(@Autowired CommandService commandService, @Autowired AnswerService answerService) {
        commandService.save(getCommandName());
        answerService.saveAnswers(answer, getCommand());
    }

    public InquiryType getType() {
        try {
            return this.getClass().getAnnotation(InquiryId.class).type();
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

    public abstract void initAnswers();

    public InquiryEntity getEntity() {
        return new CommonInquiryEntity(this);
    }
}