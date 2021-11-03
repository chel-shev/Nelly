package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Slf4j
@Getter
@Setter
public abstract class InquiryFinance extends Inquiry {

    private Long amount;
    private AccountEntity account;

    public void generate(UserEntity user) throws TelegramBotException {
        this.setUser(user);
        this.setDate(LocalDateTime.now());
        this.setClosed(false);
        InquiryEntity inquiryEntity = save();
        save();
        this.setId(inquiryEntity.getId());
        log.info("INIT Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    public void generate(InquiryEntity entity, UserEntity user) throws TelegramBotException {
        this.setUser(user);
        this.setClosed(entity.isClosed());
        if (!isNull(entity.getIn()))
            this.setAccount(entity.getIn());
        this.setDate(entity.getDate());
        this.setId(entity.getId());
        log.info("CREATE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    public boolean isReadyForProcess() {
        return !isNull(getAccount());
    }

    public InquiryEntity getEntity() {
        return new InquiryEntity(this, getAccount(), null);
    }

    public InquiryAnswer setAccountFromText(String accountName) {
        if (accountName.equals("Отмена"))
            return cancel();
        AccountEntity account = getAccount(accountName.split(" ")[1]);
        this.setAccount(account);
        save();
        return new InquiryAnswer(getUser(), getTextInfo(), KeyboardType.CANCEL);
    }

    public AccountEntity getAccount(String nameAccount) {
        return userService.getAccountList(getUser().getId()).stream()
                .filter(e -> e.getName().equals(nameAccount))
                .findFirst()
                .orElseThrow(() -> new TelegramBotException("'" + nameAccount + "' не найден!", KeyboardType.CANCEL));
    }

    public void complete() {
        this.setClosed(true);
        save();
        log.info("COMPLETE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
    }

    public InquiryAnswer cancel() {
        getInquiryService().delete(this.getEntity());
        log.info("CANCEL Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
        return new InquiryAnswer(getUser(), "Действие отменено!", KeyboardType.FINANCE);
    }

    public String getTextInfo() {
        return getType().getInfo();
    }

    public String getNameFromParam(int index) {
        return getMessage().split(":")[index].trim();
    }

    public long getValueFromParam(int index) {
        try {
            return (long) (Double.parseDouble(getMessage().split(":")[index]
                    .replace(",", ".")
                    .replace("–", "")
                    .replace("-", "")
                    .replace(" ", "")
                    .trim()) * 100);
        } catch (NumberFormatException e) {
            throw new TelegramBotException("Неверный формат!", KeyboardType.CANCEL);
        }
    }

    public boolean getDirectionFromParam(int index) {
        return !(getMessage().split(":")[index].contains("-") || getMessage().split(":")[index].contains("–"));
    }

    public boolean isDoubleParam() {
        return getMessage().split(":").length == 2;
    }
}
