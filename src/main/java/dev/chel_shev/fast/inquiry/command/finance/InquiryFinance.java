package dev.chel_shev.fast.inquiry.command.finance;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.FastFinanceInquiryEntity;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiry;
import dev.chel_shev.nelly.entity.finance.AccountEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j(topic = "inquiry")
@Getter
@Setter
public abstract class InquiryFinance extends FastCommandInquiry {

    private Long amount;
    private AccountEntity account;

    public void init(FastInquiryEntity entity, FastUserEntity userEntity) {
        super.init(entity, userEntity);
        this.account = ((FastFinanceInquiryEntity) entity).getIn();
        log.info("INIT {}", this);
    }

    public boolean isNotReadyForExecute() {
        return isNull(getAccount()) || isNull(getMessage());
    }

    public FastInquiryEntity getEntity() {
        return new FastFinanceInquiryEntity(this, null);
    }


    @Override
    public String toString() {
        return super.toString() +
                ", amount=" + amount +
                ", account=" + account.getName();
    }
}