package dev.chel_shev.nelly.inquiry.finance.loan;

import dev.chel_shev.nelly.entity.LoanEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.finance.InquiryFinance;
import dev.chel_shev.nelly.service.LoanService;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component
@InquiryId(type = InquiryType.LOAN, command = "/loan")
public class LoanInquiryFinance extends InquiryFinance {

    private final LoanService loanService;

    public LoanInquiryFinance(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public void initAnswers() {

    }

    @Override
    public InquiryAnswer logic() {
        log.info("PROCESS LoanInquiry(inquiryId: {}, text: {}, type: {}, date: {}, completed: {})", getId(), getMessage(), getType(), getDate(), isClosed());
        try {
            if (getMessage().equals("Отмена"))
                return cancel();
            else if (isDoubleParam())
                return saveLoan();
            else
                return new InquiryAnswer(getUser(), "Неверный формат!", KeyboardType.CANCEL);
        } catch (JSONException | NullPointerException e) {
            throw new TelegramBotException("Ошибка добавления!", KeyboardType.CANCEL);
        }
    }

    @Override
    public String getTextInfo() {
        Collection<LoanEntity> loanByClient = loanService.getLoanByClient(getUser());
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
        return String.format(getType().getInfo(), loanList);
    }

    private InquiryAnswer saveLoan() {
        String name = getNameFromParam(0);
        long value = getValueFromParam(1);
        boolean direction = getDirectionFromParam(1);
        setAmount(direction ? value : -1 * value);
        LoanEntity loanEntity = new LoanEntity(name, value, LocalDateTime.now(), null, direction, getAccount());
        loanService.save(loanEntity, getAccount());
        complete();
        return new InquiryAnswer(getUser(),"Займ добавлен!", KeyboardType.FINANCE);
    }
}
