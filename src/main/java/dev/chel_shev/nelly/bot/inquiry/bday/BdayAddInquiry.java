package dev.chel_shev.nelly.bot.inquiry.bday;

import dev.chel_shev.nelly.entity.inquiry.BdayInquiryEntity;
import dev.chel_shev.nelly.entity.inquiry.InquiryEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.utils.InquiryId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.google.common.base.Strings.isNullOrEmpty;
import static dev.chel_shev.nelly.type.InquiryType.BDAY_ADD;
import static java.util.Objects.isNull;

@Getter
@Setter
@Slf4j
@InquiryId(BDAY_ADD)
public class BdayAddInquiry extends Inquiry {

    private LocalDateTime bdayDate;
    private String name;

    @Override
    public void init(InquiryEntity entity, UserEntity user) throws TelegramBotException {
        super.init(entity, user);
        this.bdayDate = ((BdayInquiryEntity) entity).getBdayDate();
        this.name = ((BdayInquiryEntity) entity).getName();
        log.info("INIT {}", this);
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name) || isNull(bdayDate);
    }

    @Override
    public InquiryEntity getEntity() {
        return new BdayInquiryEntity(this);
    }
}