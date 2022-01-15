package dev.chel_shev.nelly.bot.inquiry.finance;

import dev.chel_shev.nelly.entity.finance.IncomeEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.service.IncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static dev.chel_shev.nelly.bot.inquiry.utils.InquiryUtils.*;
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
    public void executionLogic(IncomeInquiryFinance i) {
        log.info("PROCESS Income {}", i);
        try {
            if (isDoubleParam(i))
                saveIncome(i);
            else {
                i.setAnswerMessage(aSer.generateAnswer(FIRST, incomeConfig));
                i.setKeyboardType(CANCEL);
            }
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException(i.getUser(),"Ошибка добавления!", CANCEL);
        }
    }

    private void saveIncome(IncomeInquiryFinance i) {
        long value = getValueFromParam(i, 1);
        i.setAmount(value);
        String name = getNameFromParam(i, 0);
        IncomeEntity incomeEntity = new IncomeEntity(name, value, LocalDateTime.now(), null, i.getAccount());
        incomeService.save(incomeEntity, i.getAccount());
        i.setAnswerMessage(aSer.generateAnswer(SECOND, incomeConfig, i.getAccount().getInfoString()));
        i.setKeyboardType(FINANCE);
        i.setClosed(true);
    }

    public String getTextInfo(IncomeInquiryFinance i) {
        return aSer.generateAnswer(THIRD, incomeConfig);
    }
}