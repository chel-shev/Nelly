package dev.chel_shev.nelly.inquiry.finance;

import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@InquiryId(InquiryType.LOAN)
public class LoanInquiryFinance extends InquiryFinance {

}
