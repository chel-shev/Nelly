package dev.chel_shev.fast.inquiry.command.finance.transfer;

import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.FastFinanceInquiryEntity;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.finance.InquiryFinance;
import dev.chel_shev.fast.type.FastInquiryType;
import dev.chel_shev.nelly.entity.finance.AccountEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
@FastInquiryId(command = "/transfer", type = FastInquiryType.COMMAND, label = "\uD83D\uDCB0 Перевод")
public class TransferInquiryFinance extends InquiryFinance {

    @Setter
    @Getter
    private AccountEntity accountOut;

    @Override
    public void init(FastInquiryEntity entity, FastUserEntity userEntity) throws FastBotException {
        super.init(entity, userEntity);
        this.setAccount(((FastFinanceInquiryEntity) entity).getIn());
        this.accountOut = ((FastFinanceInquiryEntity) entity).getOut();
        log.info("CREATE {}", this);
    }

    @Override
    public FastInquiryEntity getEntity() {
        return new FastFinanceInquiryEntity(this, getAccountOut());
    }

    @Override
    public boolean isNotReadyForExecute() {
        return isNull(getAccount()) || isNull(accountOut) || isNull(getMessage());
    }

    @Override
    public String toString() {
        return super.toString() +
                ", accountOut=" + accountOut.getName() +
                '}';
    }
}
