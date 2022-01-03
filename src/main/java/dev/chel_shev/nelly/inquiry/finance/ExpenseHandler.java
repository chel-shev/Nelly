package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.entity.ExpenseEntity;
import dev.chel_shev.nelly.entity.ExpenseProductEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.service.ExpenseService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static dev.chel_shev.nelly.inquiry.utils.InquiryUtils.*;
import static dev.chel_shev.nelly.type.CommandLevel.THIRD;
import static dev.chel_shev.nelly.type.KeyboardType.FINANCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseHandler extends InquiryFinanceHandler<ExpenseInquiryFinance> {

    private final ExpenseService expenseService;
    private final Receipt receipt;
    private final ExpenseConfig expenseConfig;

    @Override
    public ExpenseInquiryFinance executionLogic(ExpenseInquiryFinance i) {
        try {
            if (isDoubleParam(i)) {
                return savePurchase(i);
            } else {
                return saveReceipt(i);
            }
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException(i.getUser(), "Ошибка добавления!", KeyboardType.CANCEL);
        }
    }

    private ExpenseInquiryFinance savePurchase(ExpenseInquiryFinance i) {
        String name = getNameFromParam(i, 0);
        long value = getValueFromParam(i, 1);
        i.setAmount(value);
        ExpenseProductEntity expenseProductEntity = new ExpenseProductEntity(name, null);
        ExpenseEntity expenseEntity = new ExpenseEntity(LocalDateTime.now(), value, expenseProductEntity);
        expenseService.save(expenseEntity, i.getAccount(), i.getAmount());
        i.setClosed(true);
        i.setAnswerMessage(answerService.generateAnswer(CommandLevel.FIRST, expenseConfig, i.getAccount().getInfoString()));
        i.setKeyboardType(FINANCE);
        return i;
    }

    private ExpenseInquiryFinance saveReceipt(ExpenseInquiryFinance i) throws JSONException {
        receipt.setQR(i.getMessage());
        List<ExpenseEntity> expenses = receipt.getExpenses();
        i.setAmount(receipt.getSum());
        expenseService.saveAll(expenses, i.getAccount(), i.getAmount());
        i.setClosed(true);
        i.setAnswerMessage(answerService.generateAnswer(CommandLevel.SECOND, expenseConfig));
        i.setKeyboardType(FINANCE);
        log.info("COMPLETE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return i;
    }

    public String getTextInfo(ExpenseInquiryFinance i) {
        return answerService.generateAnswer(THIRD, expenseConfig);
    }
}
