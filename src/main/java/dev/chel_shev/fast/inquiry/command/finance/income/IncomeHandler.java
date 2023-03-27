package dev.chel_shev.fast.inquiry.command.finance.income;

import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.inquiry.command.finance.InquiryFinanceHandler;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.nelly.entity.finance.IncomeEntity;
import dev.chel_shev.nelly.service.IncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

import static dev.chel_shev.fast.FastUtils.*;
import static dev.chel_shev.fast.type.FastBotCommandLevel.SECOND;
import static dev.chel_shev.fast.type.FastBotCommandLevel.THIRD;

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
                i.setAnswerMessage(answerService.generateAnswer(THIRD, incomeConfig));
                i.setKeyboardType(FastKeyboardType.REPLY);
                i.setKeyboardButtonList(Arrays.asList("Отмена"));
            }
        } catch (JSONException | NullPointerException e) {
            throw new FastBotException(i.getUser().getChatId(),"Ошибка добавления!");
        }
    }

    private void saveIncome(IncomeInquiryFinance i) {
        long value = getValueFromParam(i, 1);
        i.setAmount(value);
        String name = getNameFromParam(i, 0);
        IncomeEntity incomeEntity = new IncomeEntity(name, value, LocalDateTime.now(), null, i.getAccount());
        incomeService.save(incomeEntity, i.getAccount());
        i.setAnswerMessage(answerService.generateAnswer(SECOND, incomeConfig, i.getAccount().getInfoString()));
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(Arrays.asList("FINANCE"));
        i.setClosed(true);
    }

    public String getTextInfo(IncomeInquiryFinance i) {
        return answerService.generateAnswer(THIRD, incomeConfig);
    }
}