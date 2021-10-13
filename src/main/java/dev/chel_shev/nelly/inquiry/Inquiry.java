package dev.chel_shev.nelly.inquiry;


import dev.chel_shev.nelly.entity.CommandEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import dev.chel_shev.nelly.service.*;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
public abstract class Inquiry {

    private Long id;
    private String message;
    private LocalDateTime date;
    private boolean closed = false;

    private Map<CommandLevel, Set<String>> answer = new HashMap<>();

    private UserEntity user;
    private CommandEntity command;

    @Autowired
    protected InquiryService inquiryService;
    @Autowired
    protected CommandService commandService;
    @Autowired
    protected AnswerService answerService;
    @Autowired
    protected UserService userService;

    public void generate(InquiryEntity entity, UserEntity user) {
        this.id = entity.getId();
        this.message = entity.getMessage();
        this.closed = entity.isClosed();
        this.date = entity.getDate();
        this.user = user;
    }

    public void generate(String message, LocalDateTime date, UserEntity user) {
        this.message = message;
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
        return this.getClass().getAnnotation(InquiryId.class).command();
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

    public InquiryEntity save() {
        return inquiryService.save(this);
    }

    public abstract void initAnswers();

    public abstract InquiryAnswer logic();

    public void done() {
        this.closed = true;
    }

    public String getArgFromMassage(int index) {
        try {
            return getMessage().split(" ")[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TelegramBotException("Неверное кол-во аргументов :(");
        }
    }

    public String getLastArgsPast(int index) {
        try {
            List<String> skip = Arrays.stream(getMessage().split(" ")).skip(index + 1).collect(Collectors.toList());
            return Strings.join(skip, ' ');
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TelegramBotException("Неверное кол-во аргументов :(");
        }
    }

    public boolean validationArgs(int count, String sign) {
        int length = getMessage().split(" ").length;
        return switch (sign) {
            case ">" -> length > count;
            case ">=" -> length >= count;
            case "<" -> length < count;
            case "<=" -> length <= count;
            case "==" -> length == count;
            default -> throw new TelegramBotException("Обратись к админу :(");
        };
    }
}