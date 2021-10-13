package dev.chel_shev.nelly.inquiry.finance.transfer;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.TransferEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.finance.InquiryFinance;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.service.TransferService;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Slf4j
@Component
@InquiryId(type = InquiryType.TRANSFER, command = "/transfer")
public class TransferInquiryFinance extends InquiryFinance {

    private final TransferService transferService;
    private AccountEntity accountOut;

    public TransferInquiryFinance(TransferService transferService) {
        this.transferService = transferService;
    }

    @Override
    public void generate(InquiryEntity entity, UserEntity user) throws TelegramBotException {
        this.setUser(user);
        this.setClosed(entity.isClosed());
        if (!isNull(entity.getIn()))
            this.setAccount(entity.getIn());
        this.setDate(entity.getDate());
        this.setId(entity.getId());
        this.accountOut = entity.getOut();
        log.info("CREATE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    @Override
    public void initAnswers() {

    }

    @Override
    public InquiryAnswer logic() {
        log.info("PROCESS TransferInquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
        try {
            if (getMessage().equals("Отмена"))
                return cancel();
            return saveTransfer();
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException("Ошибка добавления!", KeyboardType.CANCEL);
        }
    }

    public InquiryAnswer saveTransfer() {
        long value = getValueFromParam(0);
        setAmount(value);
        TransferEntity transferEntity = new TransferEntity(accountOut, getAccount(), value, LocalDateTime.now());
        transferService.save(transferEntity);
        complete();
        return new InquiryAnswer(getUser(), "Перевод добавлен!", KeyboardType.INQUIRIES);
    }

    @Override
    public InquiryEntity getEntity() {
        return new InquiryEntity(this, getAccount(), accountOut);
    }

    @Override
    public InquiryAnswer setAccountFromText(String accountName) {
        if (accountName.equals("Отмена"))
            return cancel();
        AccountEntity account = getAccount(accountName.split(" ")[1]);
        if (isNull(this.getAccount())) {
            this.setAccount(account);
            save();
            return new InquiryAnswer(getUser(), "Выберите счет, на который хотите совершить перевод:", KeyboardType.ACCOUNTS);
        } else {
            this.accountOut = account;
            save();
            return new InquiryAnswer(getUser(), getTextInfo(), KeyboardType.CANCEL);
        }
    }

    @Override
    public boolean isReadyForProcess() {
        return !(isNull(getAccount()) || isNull(accountOut));
    }
}
