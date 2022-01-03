package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.entity.IncomeEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.service.IncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static dev.chel_shev.nelly.inquiry.utils.InquiryUtils.*;
import static dev.chel_shev.nelly.type.CommandLevel.*;
import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;
import static dev.chel_shev.nelly.type.KeyboardType.FINANCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomeHandler extends InquiryFinanceHandler<IncomeInquiryFinance> {

    private final IncomeService incomeService;
    private final IncomeConfig incomeConfig;

    @Override
    public IncomeInquiryFinance executionLogic(IncomeInquiryFinance i) {
        log.info("PROCESS IncomeInquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        try {
            if (isDoubleParam(i))
                return saveIncome(i);
            else {
                i.setAnswerMessage(answerService.generateAnswer(FIRST, incomeConfig));
                i.setKeyboardType(CANCEL);
                return i;
            }
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException(i.getUser(),"Ошибка добавления!", CANCEL);
        }
    }

    private IncomeInquiryFinance saveIncome(IncomeInquiryFinance i) {
        long value = getValueFromParam(i, 1);
        i.setAmount(value);
        String name = getNameFromParam(i, 0);
        IncomeEntity incomeEntity = new IncomeEntity(name, value, LocalDateTime.now(), null, i.getAccount());
        incomeService.save(incomeEntity, i.getAccount());
        i.setAnswerMessage(answerService.generateAnswer(SECOND, incomeConfig, i.getAccount().getInfoString()));
        i.setKeyboardType(FINANCE);
        i.setClosed(true);
        return i;
    }

    public String getTextInfo(IncomeInquiryFinance i) {
        return answerService.generateAnswer(THIRD, incomeConfig);
    }
}