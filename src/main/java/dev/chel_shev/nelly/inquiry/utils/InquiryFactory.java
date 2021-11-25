package dev.chel_shev.nelly.inquiry.utils;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.bday.BdayAddInquiry;
import dev.chel_shev.nelly.inquiry.bday.BdayRemoveInquiry;
import dev.chel_shev.nelly.inquiry.bot.UnknownInquiry;
import dev.chel_shev.nelly.inquiry.bot.UnknownUserInquiry;
import dev.chel_shev.nelly.inquiry.finance.ExpenseInquiryFinance;
import dev.chel_shev.nelly.inquiry.finance.IncomeInquiryFinance;
import dev.chel_shev.nelly.inquiry.finance.LoanInquiryFinance;
import dev.chel_shev.nelly.inquiry.finance.TransferInquiryFinance;
import dev.chel_shev.nelly.inquiry.bot.StartInquiry;
import dev.chel_shev.nelly.inquiry.bot.StopInquiry;
import dev.chel_shev.nelly.inquiry.keyboard.KeyboardInquiry;
import dev.chel_shev.nelly.inquiry.workout.EyeWorkoutInquiry;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.stereotype.Component;

@Component
public class InquiryFactory<I extends Inquiry> {

    @SuppressWarnings("unchecked")
    public I create(InquiryType type) {
        Inquiry inquiry = switch (type) {
            case EXPENSE -> new ExpenseInquiryFinance();
            case INCOME -> new IncomeInquiryFinance();
            case LOAN -> new LoanInquiryFinance();
            case TRANSFER -> new TransferInquiryFinance();
            case BDAY_ADD -> new BdayAddInquiry();
            case BDAY_REMOVE -> new BdayRemoveInquiry();
            case START -> new StartInquiry();
            case STOP -> new StopInquiry();
            case WORKOUT -> new EyeWorkoutInquiry();
            case UNKNOWN -> new UnknownInquiry();
            case UNKNOWN_USER -> new UnknownUserInquiry();
            case KEYBOARD -> new KeyboardInquiry();
            default -> null;
        };
        return (I) inquiry;
    }

    public I create(String command) {
        return create(InquiryType.getFromCommand(command));
    }
}
