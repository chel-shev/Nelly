package dev.chel_shev.nelly.inquiry;

import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import dev.chel_shev.nelly.inquiry.prototype.KeyboardInquiry;
import dev.chel_shev.nelly.inquiry.prototype.UnknownInquiry;
import dev.chel_shev.nelly.inquiry.prototype.UnknownUserInquiry;
import dev.chel_shev.nelly.inquiry.prototype.bday.BdayInquiry;
import dev.chel_shev.nelly.inquiry.prototype.bday.BdayRemoveInquiry;
import dev.chel_shev.nelly.inquiry.prototype.finance.ExpenseInquiryFinance;
import dev.chel_shev.nelly.inquiry.prototype.finance.IncomeInquiryFinance;
import dev.chel_shev.nelly.inquiry.prototype.finance.LoanInquiryFinance;
import dev.chel_shev.nelly.inquiry.prototype.finance.TransferInquiryFinance;
import dev.chel_shev.nelly.inquiry.prototype.start.StartInquiry;
import dev.chel_shev.nelly.inquiry.prototype.stop.StopInquiry;
import dev.chel_shev.nelly.inquiry.prototype.workout.EyeWorkoutInquiry;
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
            case BDAY -> new BdayInquiry();
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
