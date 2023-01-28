package dev.chel_shev.nelly.bot.inquiry.finance;

import dev.chel_shev.nelly.entity.event.finance.AccountEntity;
import dev.chel_shev.nelly.entity.inquiry.FinanceInquiryEntity;
import dev.chel_shev.nelly.entity.inquiry.InquiryEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.bot.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
        log.info("CREATE {}", this);
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
