package dev.chel_shev.fast.inquiry.keyboard.finance;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.finance.expense.ExpenseInquiryFinance;
import dev.chel_shev.fast.inquiry.command.finance.income.IncomeInquiryFinance;
import dev.chel_shev.fast.inquiry.command.finance.loan.LoanInquiryFinance;
import dev.chel_shev.fast.inquiry.command.finance.transfer.TransferInquiryFinance;
import dev.chel_shev.fast.inquiry.keyboard.FastKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.cancel.CancelKeyboardInquiry;
import dev.chel_shev.fast.type.FastInquiryType;

@FastInquiryId(command = "/finance", buttons = {ExpenseInquiryFinance.class, IncomeInquiryFinance.class, LoanInquiryFinance.class, TransferInquiryFinance.class, CancelKeyboardInquiry.class}, type = FastInquiryType.KEYBOARD_SUBSCRIPTION, label = "\uD83D\uDCB0 Финансы")
public class FinanceKeyboardInquiry extends FastKeyboardInquiry {
}