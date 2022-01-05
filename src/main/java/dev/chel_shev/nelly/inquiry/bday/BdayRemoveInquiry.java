package dev.chel_shev.nelly.inquiry.bday;

import dev.chel_shev.nelly.entity.BdayInquiryEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Strings.isNullOrEmpty;
import static dev.chel_shev.nelly.type.InquiryType.BDAY_REMOVE;

@Getter
@Setter
@Slf4j
@InquiryId(BDAY_REMOVE)
public class BdayRemoveInquiry extends Inquiry {

    private String name;

    @Override
    public void init(InquiryEntity entity, UserEntity user) throws TelegramBotException {
        super.init(entity, user);
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
}
