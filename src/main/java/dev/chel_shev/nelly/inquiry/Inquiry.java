package dev.chel_shev.nelly.inquiry;


import dev.chel_shev.nelly.entity.CommandEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.CommandService;
import dev.chel_shev.nelly.service.InquiryService;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
public abstract class Inquiry {

    private Long id;
    private String massage;
    private LocalDateTime date;
    private boolean closed = false;

    private Map<CommandLevel, Set<String>> answer = new HashMap<>();

    private UserEntity user;
    private CommandEntity command;

    protected final InquiryService inquiryService;
    protected final CommandService commandService;
    protected final AnswerService answerService;

    protected Inquiry(InquiryService inquiryService, CommandService commandService, AnswerService answerService) {
        this.inquiryService = inquiryService;
        this.commandService = commandService;
        this.answerService = answerService;
    }

    public void generate(InquiryEntity entity, UserEntity user) {
        this.id = entity.getId();
        this.massage = entity.getMassage();
        this.closed = entity.isClosed();
        this.date = entity.getDate();
        this.user = user;
    }

    public void generate(String massage, LocalDateTime date, UserEntity user) {
        this.massage = massage;
        this.date = date;
        this.user = user;
    }

    public InquiryType getType() {
        try {
            return this.getClass().getAnnotation(InquiryId.class).type();
        } catch (Exception e) {
            throw new TelegramBotException("Inquiry not defined!");
        }
    }

    public String getCommandFromAnnotation() {
        if (getType() == InquiryType.COMMAND)
            return this.getClass().getAnnotation(InquiryId.class).command();
        throw new TelegramBotException("The inquiry must be of the Command type!");
    }

    @Autowired
    public void registerMyself(InquiryService inquiryService) {
        inquiryService.register(getCommandFromAnnotation(), this);
        command = commandService.save(getCommandFromAnnotation());
        initAnswers();
        answerService.saveAnswers(getAnswer(), command);
    }

    public InquiryAnswer process() {
        InquiryAnswer answer = logic();
        save();
        return answer;
    }

    public void save() {
        inquiryService.save(this);
    }

    public abstract void initAnswers();

    public abstract InquiryAnswer logic();

    public void done() {
        this.closed = true;
    }

    public String getArgFromMassage(int index) {
        try {
            return getMassage().split(" ")[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TelegramBotException("Неверное кол-во аргументов :(");
        }
    }

    public String getLastArgsPast(int index) {
        try {
            List<String> skip = Arrays.stream(getMassage().split(" ")).skip(index + 1).collect(Collectors.toList());
            return Strings.join(skip, ' ');
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TelegramBotException("Неверное кол-во аргументов :(");
        }
    }

    public boolean validationArgs(int count, String sign) {
        if (!"> >= < <= ==".contains(sign))
            throw new TelegramBotException("Обратись к админу :(");
        int length = getMassage().split(" ").length;
        switch (sign) {
            case ">":
                if (length <= count) return false;
                break;
            case ">=":
                if (length < count) return false;
                break;
            case "<":
                if (length >= count) return false;
                break;
            case "<=":
                if (length > count) return false;
                break;
            case "==":
                if (length != count) return false;
                break;
        }
        return true;
    }
}