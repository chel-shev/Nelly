package dev.chel_shev.nelly.inquiry.bday;

import dev.chel_shev.nelly.entity.BdayInquiryEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
@Setter
@Component
@Scope("prototype")
@Slf4j
@InquiryId(type = InquiryType.BDAY_REMOVE)
public class BdayRemoveInquiry extends Inquiry {

    private String name;

    @Override
    public void init(InquiryEntity entity, UserEntity user) throws TelegramBotException {
        setMessage(entity.getMessage());
        setClosed(entity.isClosed());
        setDate(entity.getDate());
        setAnswerMessage(entity.getAnswerMessage());
        setKeyboardType(entity.getKeyboardType());
        setCommand(entity.getCommand());
        setUser(user);
        this.name = ((BdayInquiryEntity) entity).getName();
        log.info("INIT Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name);
    }

    @Override
    public InquiryEntity getEntity() {
        return new BdayInquiryEntity(this);
    }

    @Override
    public BdayRemoveInquiry getInstance() {
        return new BdayRemoveInquiry();
    }
}
