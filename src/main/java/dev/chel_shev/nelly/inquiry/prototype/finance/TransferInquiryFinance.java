package dev.chel_shev.nelly.inquiry.prototype.finance;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.FinanceInquiryEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
@Scope("prototype")
@InquiryId(type = InquiryType.TRANSFER, command = "/transfer")
public class TransferInquiryFinance extends InquiryFinance {

    @Setter
    @Getter
    private AccountEntity accountOut;

    @Override
    public void generate(InquiryEntity entity, UserEntity user) throws TelegramBotException {
        this.setUser(user);
        this.setClosed(entity.isClosed());
        this.setAccount(((FinanceInquiryEntity) entity).getIn());
        this.setDate(entity.getDate());
        this.setId(entity.getId());
        this.setCommand(entity.getCommand());
        this.accountOut = ((FinanceInquiryEntity) entity).getOut();
        log.info("CREATE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    @Override
    public void initAnswers() {

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
