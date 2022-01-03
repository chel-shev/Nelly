package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@InquiryId(type = InquiryType.INCOME)
public class IncomeInquiryFinance extends InquiryFinance {

    @Override
    public IncomeInquiryFinance getInstance() {
        return new IncomeInquiryFinance();
    }
}