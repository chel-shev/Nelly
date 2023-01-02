package dev.chel_shev.nelly.bot.inquiry.finance;

import dev.chel_shev.nelly.entity.finance.AccountEntity;
import dev.chel_shev.nelly.entity.inquiry.FinanceInquiryEntity;
import dev.chel_shev.nelly.entity.inquiry.InquiryEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
@Getter
@Setter
public abstract class InquiryFinance extends Inquiry {

    private Long amount;
    private AccountEntity account;

    public void init(InquiryEntity entity, UserEntity user) {
        super.init(entity, user);
        this.account = ((FinanceInquiryEntity) entity).getIn();
        log.info("INIT {}", this);
    }

    public boolean isNotReadyForExecute() {
        return isNull(getAccount()) || isNull(getMessage());
    }

    public InquiryEntity getEntity() {
        return new FinanceInquiryEntity(this, null);
    }
}