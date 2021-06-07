package dev.chel_shev.nelly.inquiry;


import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.service.InquiryService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
public abstract class Inquiry {

    private Long id;
    private String massage;
    private LocalDateTime date;
    private boolean closed = false;

    private UserEntity user;

    protected final InquiryService service;

    protected Inquiry(InquiryService service) {
        this.service = service;
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

    public String getCommand() {
        if (getType() == InquiryType.COMMAND)
            return this.getClass().getAnnotation(InquiryId.class).command();
        throw new TelegramBotException("The inquiry must be of the Command type!");
    }

    @Autowired
    public void registerMyself(InquiryService inquiryService) {
        inquiryService.register(getCommand(), this);
    }

    public InquiryAnswer process() {
        InquiryAnswer answer = logic();
        save();
        return answer;
    }

    public void save() {
        service.save(this);
    }

    public abstract InquiryAnswer logic();

    public String getArgFromMassage(int index) {
        return getMassage().split(" ")[index];
    }
}