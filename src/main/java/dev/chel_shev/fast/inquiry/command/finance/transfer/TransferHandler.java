package dev.chel_shev.fast.inquiry.command.finance.transfer;

import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.inquiry.command.finance.InquiryFinanceHandler;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.nelly.entity.finance.AccountEntity;
import dev.chel_shev.nelly.entity.finance.TransferEntity;
import dev.chel_shev.nelly.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.Arrays;

import static dev.chel_shev.fast.FastUtils.getValueFromParam;
import static dev.chel_shev.fast.type.FastBotCommandLevel.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferHandler extends InquiryFinanceHandler<TransferInquiryFinance> {

    private final TransferService transferService;
    private final TransferConfig transferConfig;

    @Override
    public void executionLogic(TransferInquiryFinance i) {
        log.info("PROCESS Transfer {}", i);
        try {
            saveTransfer(i);
        } catch (JSONException | NullPointerException e) {
            throw new FastBotException(i.getUser().getChatId(), "Ошибка добавления!");
        }
    }

    public void saveTransfer(TransferInquiryFinance i) {
        long value = getValueFromParam(i, 0);
        i.setAmount(value);
        TransferEntity transferEntity = new TransferEntity(i.getAccountOut(), i.getAccount(), value, LocalDateTime.now());
        transferService.save(transferEntity);
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(Arrays.asList("FINANCE"));
        i.setAnswerMessage(answerService.generateAnswer(FIRST, transferConfig, i.getAccountOut().getInfoString(), i.getAccount().getInfoString()));
        i.setClosed(true);
    }

    @Override
    public void preparationLogic(TransferInquiryFinance i, Message message) {
        if (nonNull(i.getAccount()) && nonNull(i.getAccountOut())) {
            if (message.hasPhoto())
                i.setMessage(botResource.getQRDataFromPhoto(message));
            else
                i.setMessage(message.getText());
            return;
        }
        if (fastUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(SECOND, transferConfig));
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtonList(Arrays.asList("аккаунты"));
            return;
        }
        AccountEntity account = getAccount(i, message.getText().split(" ")[1]);
        if (isNull(i.getAccount())) {
            i.setAccount(account);
            i.setAnswerMessage(answerService.generateAnswer(THIRD, transferConfig));
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtonList(Arrays.asList("аккаунты"));
        } else {
            i.setAccountOut(account);
            i.setAnswerMessage(answerService.generateAnswer(FOURTH, transferConfig));
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtonList(Arrays.asList("Отмена"));
        }
    }

    public String getTextInfo(TransferInquiryFinance i) {
        return answerService.generateAnswer(THIRD, transferConfig);
    }
}