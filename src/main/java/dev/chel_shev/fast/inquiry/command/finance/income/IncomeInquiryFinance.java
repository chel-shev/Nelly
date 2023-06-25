package dev.chel_shev.fast.inquiry.command.finance.income;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.finance.InquiryFinance;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FastInquiryId(command = "/income", type = FastInquiryType.COMMAND, label = "\uD83D\uDCB0 Доход")
public class IncomeInquiryFinance extends InquiryFinance {
    @Override
    public String toString() {
        return super.toString() +
                '}';
    }
}