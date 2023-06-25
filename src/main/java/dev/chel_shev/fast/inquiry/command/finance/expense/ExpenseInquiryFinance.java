package dev.chel_shev.fast.inquiry.command.finance.expense;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.finance.InquiryFinance;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FastInquiryId(command = "/expense", type = FastInquiryType.COMMAND, label = "\uD83D\uDCB0 Расход")
public class ExpenseInquiryFinance extends InquiryFinance {
    @Override
    public String toString() {
        return super.toString() +
                '}';
    }
}