package dev.chel_shev.nelly.inquiry.handler.finance;

import dev.chel_shev.nelly.entity.ExpenseEntity;
import dev.chel_shev.nelly.entity.ExpenseProductEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.prototype.finance.ExpenseInquiryFinance;
import dev.chel_shev.nelly.receipt.Receipt;
import dev.chel_shev.nelly.service.ExpenseService;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static dev.chel_shev.nelly.inquiry.InquiryUtils.*;
import static dev.chel_shev.nelly.type.KeyboardType.FINANCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseHandler extends InquiryFinanceHandler<ExpenseInquiryFinance> {

    private final ExpenseService expenseService;
    private final Receipt receipt;

    @Override
    public ExpenseInquiryFinance executionLogic(ExpenseInquiryFinance i) {
        log.info("PROCESS ExpenseInquiry(inquiryId: {}, text: {}, type: {}, date: {}, completed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        try {
            if (isDoubleParam(i)) {
                return savePurchase(i);
            } else {
                return saveReceipt(i);
            }
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException("Ошибка добавления!", KeyboardType.CANCEL);
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
        i.setAnswerMessage("Расход добавлен!");
        i.setKeyboardType(FINANCE);
        return i;
    }

    private ExpenseInquiryFinance saveReceipt(ExpenseInquiryFinance i) throws JSONException {
        receipt.setQR(i.getMessage());
        List<ExpenseEntity> expenses = receipt.getExpenses();
        i.setAmount(receipt.getSum());
        expenseService.saveAll(expenses, i.getAccount(), i.getAmount());
        i.setClosed(true);
        i.setAnswerMessage("Чек добавлен!");
        i.setKeyboardType(FINANCE);
        log.info("COMPLETE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return i;
    }
}
