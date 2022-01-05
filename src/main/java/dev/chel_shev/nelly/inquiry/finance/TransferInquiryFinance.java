package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.FinanceInquiryEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@InquiryId(InquiryType.TRANSFER)
public class TransferInquiryFinance extends InquiryFinance {

    @Setter
    @Getter
    private AccountEntity accountOut;

    @Override
    public void init(InquiryEntity entity, UserEntity user) throws TelegramBotException {
        super.init(entity, user);
        this.setAccount(((FinanceInquiryEntity) entity).getIn());
        this.accountOut = ((FinanceInquiryEntity) entity).getOut();
        log.info("CREATE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    @Override
    public InquiryEntity getEntity() {
        return new FinanceInquiryEntity(this, getAccountOut());
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNull(getAccount()) || isNull(accountOut) || isNull(getMessage());
    }
}
