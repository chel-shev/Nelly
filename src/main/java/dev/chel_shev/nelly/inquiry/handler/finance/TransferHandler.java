package dev.chel_shev.nelly.inquiry.handler.finance;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.TransferEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.prototype.finance.TransferInquiryFinance;
import dev.chel_shev.nelly.service.TransferService;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;

import static dev.chel_shev.nelly.inquiry.InquiryUtils.getValueFromParam;
import static dev.chel_shev.nelly.type.KeyboardType.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferHandler extends InquiryFinanceHandler<TransferInquiryFinance> {

    private final TransferService transferService;

    @Override
    public TransferInquiryFinance executionLogic(TransferInquiryFinance i) {
        log.info("PROCESS TransferInquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        try {
            return saveTransfer(i);
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException("Ошибка добавления!", CANCEL);
        }
    }

    public TransferInquiryFinance saveTransfer(TransferInquiryFinance i) {
        long value = getValueFromParam(i, 0);
        i.setAmount(value);
        TransferEntity transferEntity = new TransferEntity(i.getAccountOut(), i.getAccount(), value, LocalDateTime.now());
        transferService.save(transferEntity);
        i.setKeyboardType(FINANCE);
        i.setAnswerMessage("Перевод добавлен!");
        i.setClosed(true);
        return i;
    }

    @Override
    public TransferInquiryFinance preparationLogic(TransferInquiryFinance i, Message message) {
        if (nonNull(i.getAccount()) && nonNull(i.getAccountOut())) {
            if (message.hasPhoto())
                i.setMessage(botResources.getQRDataFromPhoto(message));
            else
                i.setMessage(message.getText());
            return i;
        }
        if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage("Выберите счет, с которым будет производится операция:");
            i.setKeyboardType(ACCOUNTS);
            return i;
        }
        AccountEntity account = getAccount(i, message.getText().split(" ")[1]);
        if (isNull(i.getAccount())) {
            i.setAccount(account);
            i.setAnswerMessage("Выберите счет, на который хотите совершить перевод:");
            i.setKeyboardType(ACCOUNTS);
        } else {
            i.setAccountOut(account);
            i.setAnswerMessage(getTextInfo(i));
            i.setKeyboardType(CANCEL);
        }
        return i;
    }
}