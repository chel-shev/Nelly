package dev.chel_shev.fast.inquiry.command.finance.loan;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.finance.InquiryFinance;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FastInquiryId(command = "/loan", type = FastInquiryType.COMMAND, label = "\uD83D\uDCB0 Займ")
public class LoanInquiryFinance extends InquiryFinance {

}
