package dev.chel_shev.nelly.inquiry.prototype.finance;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.FinanceInquiryEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import static java.util.Objects.isNull;

@Slf4j
@Getter
@Setter
@Scope("prototype")
public abstract class InquiryFinance extends Inquiry {

    private Long amount;
    private AccountEntity account;

    public void init(InquiryEntity entity, UserEntity user) {
        this.setUser(user);
        this.setClosed(entity.isClosed());
        this.setAccount(((FinanceInquiryEntity) entity).getIn());
        this.setDate(entity.getDate());
        this.setId(entity.getId());
        this.setCommand(entity.getCommand());
        this.setAnswerMessage(entity.getAnswerMessage());
        this.setKeyboardType(entity.getKeyboardType());
        log.info("INIT Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    public boolean isNotReadyForExecute() {
        return isNull(getAccount()) || isNull(getMessage());
    }

    public InquiryEntity getEntity() {
        return new FinanceInquiryEntity(this, null);
    }
}