package dev.chel_shev.nelly.inquiry.prototype.finance;

import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@NoArgsConstructor
@InquiryId(type = InquiryType.LOAN, command = "/loan")
public class LoanInquiryFinance extends InquiryFinance {

    @Override
    public void initAnswers() {

    }
}
