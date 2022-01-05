package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.FinanceInquiryEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.inquiry.Inquiry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

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
        log.info("INIT Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    public boolean isNotReadyForExecute() {
        return isNull(getAccount()) || isNull(getMessage());
    }

    public InquiryEntity getEntity() {
        return new FinanceInquiryEntity(this, null);
    }
}