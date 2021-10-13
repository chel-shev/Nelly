package dev.chel_shev.nelly.inquiry.finance.expense;

import dev.chel_shev.nelly.entity.ExpenseEntity;
import dev.chel_shev.nelly.entity.ExpenseProductEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.finance.InquiryFinance;
import dev.chel_shev.nelly.receipt.Receipt;
import dev.chel_shev.nelly.service.ExpenseService;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@InquiryId(type = InquiryType.EXPENSE, command = "/expense")
public class ExpenseInquiryFinance extends InquiryFinance {

    private final ExpenseService expenseService;
    private final Receipt receipt;

    public ExpenseInquiryFinance(ExpenseService expenseService, Receipt receipt) {
        this.expenseService = expenseService;
        this.receipt = receipt;
    }

    @Override
    public void initAnswers() {

    }

    @Override
    public InquiryAnswer logic() {
        log.info("PROCESS ExpenseInquiry(inquiryId: {}, text: {}, type: {}, date: {}, completed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
        try {
            if (getMessage().equals("Отмена")) {
                return cancel();
            } else if (isDoubleParam()) {
                return savePurchase();
            } else {
                return saveReceipt();
            }
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException("Ошибка добавления!", KeyboardType.CANCEL);
        }
    }

    private InquiryAnswer savePurchase() {
        String name = getNameFromParam(0);
        long value = getValueFromParam(1);
        setAmount(value);
        ExpenseProductEntity expenseProductEntity = new ExpenseProductEntity(name, null);
        ExpenseEntity expenseEntity = new ExpenseEntity(LocalDateTime.now(), value, expenseProductEntity);
        expenseService.save(expenseEntity, getAccount(), getAmount());
        complete();
        return new InquiryAnswer(getUser(), "Расход добавлен!", KeyboardType.INQUIRIES);
    }

    private InquiryAnswer saveReceipt() throws JSONException {
        receipt.setQR(getMessage());
        List<ExpenseEntity> expenses = receipt.getExpenses();
        setAmount(receipt.getSum());
        expenseService.saveAll(expenses, getAccount(), getAmount());
        complete();
        receipt.clear();
        return new InquiryAnswer(getUser(), "Чек добавлен!", KeyboardType.INQUIRIES);
    }
}
