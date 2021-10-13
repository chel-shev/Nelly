package dev.chel_shev.nelly.inquiry.finance.income;

import dev.chel_shev.nelly.entity.IncomeEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.finance.InquiryFinance;
import dev.chel_shev.nelly.service.IncomeService;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@InquiryId(type = InquiryType.INCOME, command = "/income")
public class IncomeInquiryFinance extends InquiryFinance {

    private final IncomeService incomeService;

    public IncomeInquiryFinance(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Override
    public void initAnswers() {

    }

    @Override
    public InquiryAnswer logic() {
        log.info("PROCESS IncomeInquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
        try {
            if (getMessage().equals("Отмена"))
                return cancel();
            else if (isDoubleParam())
                return saveIncome();
            else
                return new InquiryAnswer(getUser(), "Неверный формат!", KeyboardType.CANCEL);
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException("Ошибка добавления!", KeyboardType.CANCEL);
        }
    }

    private InquiryAnswer saveIncome() {
        long value = getValueFromParam(1);
        setAmount(value);
        String name = getNameFromParam(0);
        IncomeEntity incomeEntity = new IncomeEntity(name, value, LocalDateTime.now(), null, getAccount());
        incomeService.save(incomeEntity, getAccount());
        complete();
        return new InquiryAnswer(getUser(), "Доход добавлен!", KeyboardType.INQUIRIES);
    }
}