package dev.chel_shev.fast.inquiry.command.finance.expense;

import dev.chel_shev.fast.FastBotException;
import dev.chel_shev.fast.inquiry.command.finance.InquiryFinanceHandler;
import dev.chel_shev.fast.inquiry.command.finance.Receipt;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.nelly.entity.finance.ExpenseEntity;
import dev.chel_shev.nelly.entity.finance.ExpenseProductEntity;
import dev.chel_shev.nelly.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static dev.chel_shev.fast.FastUtils.*;
import static dev.chel_shev.fast.type.FastBotCommandLevel.THIRD;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseHandler extends InquiryFinanceHandler<ExpenseInquiryFinance> {

    private final ExpenseService expenseService;
    private final ExpenseConfig expenseConfig;

    @Override
    public void executionLogic(ExpenseInquiryFinance i) {
        try {
            if (isDoubleParam(i)) {
                savePurchase(i);
            } else {
                saveReceipt(i);
            }
        } catch (JSONException | NullPointerException e) {
            throw new FastBotException(i.getUser().getChatId(), "Ошибка добавления!");
        }
    }

    private void savePurchase(ExpenseInquiryFinance i) {
        String name = getNameFromParam(i, 0);
        long value = getValueFromParam(i, 1);
        i.setAmount(value);
        ExpenseProductEntity expenseProductEntity = new ExpenseProductEntity(name, null);
        ExpenseEntity expenseEntity = new ExpenseEntity(LocalDateTime.now(), value, expenseProductEntity);
        expenseService.save(expenseEntity, i.getAccount(), i.getAmount());
        i.setClosed(true);
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, expenseConfig, i.getAccount().getInfoString()));
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(Arrays.asList("FINANCE"));
    }

    private void saveReceipt(ExpenseInquiryFinance i) throws JSONException {
        Receipt receipt = new Receipt();
        receipt.setQR(i.getMessage());
        List<ExpenseEntity> expenses = receipt.getExpenses();
        i.setAmount(receipt.getSum());
        expenseService.saveAll(expenses, i.getAccount(), i.getAmount());
        i.setClosed(true);
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.SECOND, expenseConfig));
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(Arrays.asList("FINANCE"));
        log.info("COMPLETE {}", i);
    }

    public String getTextInfo(ExpenseInquiryFinance i) {
        return answerService.generateAnswer(THIRD, expenseConfig);
    }
}
