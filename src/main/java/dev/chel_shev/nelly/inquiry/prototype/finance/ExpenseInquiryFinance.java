package dev.chel_shev.nelly.inquiry.prototype.finance;

import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@InquiryId(type = InquiryType.EXPENSE, command = "/expense")
public class ExpenseInquiryFinance extends InquiryFinance {

    @Override
    public void initAnswers() {

    }
}