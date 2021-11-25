package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.entity.LoanEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.service.LoanService;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static dev.chel_shev.nelly.inquiry.utils.InquiryUtils.*;
import static dev.chel_shev.nelly.type.CommandLevel.*;
import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;
import static dev.chel_shev.nelly.type.KeyboardType.FINANCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanHandler extends InquiryFinanceHandler<LoanInquiryFinance> {

    private final LoanService loanService;

    @Override
    public LoanInquiryFinance executionLogic(LoanInquiryFinance i) {
        log.info("PROCESS LoanInquiry(inquiryId: {}, text: {}, type: {}, date: {}, completed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        try {
            if (isDoubleParam(i))
                return saveLoan(i);
            else{
                i.setAnswerMessage(answerService.generateAnswer(FIRST, i));
                i.setKeyboardType(CANCEL);
                return i;
            }
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException(i.getUser(), "Ошибка добавления!", KeyboardType.CANCEL);
        }
    }

    @Override
    public String getTextInfo(LoanInquiryFinance i) {
        Collection<LoanEntity> loanByClient = loanService.getLoanByClient(i.getUser());
        String loanList = loanByClient.stream()
                .collect(Collectors.groupingBy(LoanEntity::getName))
                .values()
                .stream()
                .map(e -> {
                    String name = e.get(0).getName();
                    long amount = e.stream().mapToLong(l -> l.getDirection() ? l.getAmount() : -1 * l.getAmount()).sum();
                    return new LoanEntity(name, amount);
                })
                .filter(e -> e.getAmount() != 0)
                .map(e -> "` " + e.getName() + ": " + String.format("%.2f", e.getAmount() / 100d) + " `\r\n")
                .collect(Collectors.joining());
        return String.format(answerService.generateAnswer(THIRD, i), loanList);
    }

    private LoanInquiryFinance saveLoan(LoanInquiryFinance i) {
        String name = getNameFromParam(i, 0);
        long value = getValueFromParam(i, 1);
        boolean direction = getDirectionFromParam(i, 1);
        i.setAmount(direction ? value : -1 * value);
        LoanEntity loanEntity = new LoanEntity(name, value, LocalDateTime.now(), null, direction, i.getAccount());
        loanService.save(loanEntity, i.getAccount());
        i.setAnswerMessage(answerService.generateAnswer(SECOND, i, i.getAccount().getInfoString()));
        i.setKeyboardType(FINANCE);
        i.setClosed(true);
        return i;
    }
}
