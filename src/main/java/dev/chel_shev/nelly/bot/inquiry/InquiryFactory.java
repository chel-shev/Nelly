package dev.chel_shev.nelly.bot.inquiry;

import dev.chel_shev.nelly.bot.inquiry.workout.WorkoutAddInquiry;
import dev.chel_shev.nelly.bot.inquiry.workout.WorkoutRemoveInquiry;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.inquiry.bday.BdayAddInquiry;
import dev.chel_shev.nelly.bot.inquiry.bday.BdayRemoveInquiry;
import dev.chel_shev.nelly.bot.inquiry.bot.StartInquiry;
import dev.chel_shev.nelly.bot.inquiry.bot.StopInquiry;
import dev.chel_shev.nelly.bot.inquiry.bot.UnknownInquiry;
import dev.chel_shev.nelly.bot.inquiry.bot.UnknownUserInquiry;
import dev.chel_shev.nelly.bot.inquiry.finance.ExpenseInquiryFinance;
import dev.chel_shev.nelly.bot.inquiry.finance.IncomeInquiryFinance;
import dev.chel_shev.nelly.bot.inquiry.finance.LoanInquiryFinance;
import dev.chel_shev.nelly.bot.inquiry.finance.TransferInquiryFinance;
import dev.chel_shev.nelly.bot.inquiry.keyboard.KeyboardInquiry;
import dev.chel_shev.nelly.bot.inquiry.reminder.ReminderAddInquiry;
import dev.chel_shev.nelly.bot.inquiry.reminder.ReminderRemoveInquiry;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@RequiredArgsConstructor
public class InquiryFactory<I extends Inquiry> {

    @SuppressWarnings("unchecked")
    public I getInquiry(InquiryType type) {
        return switch (type) {
            case EXPENSE -> (I) new ExpenseInquiryFinance();
            case INCOME -> (I) new IncomeInquiryFinance();
            case LOAN -> (I) new LoanInquiryFinance();
            case TRANSFER -> (I) new TransferInquiryFinance();
            case BDAY_ADD -> (I) new BdayAddInquiry();
            case BDAY_REMOVE -> (I) new BdayRemoveInquiry();
            case REMINDER_ADD -> (I) new ReminderAddInquiry();
            case REMINDER_REMOVE -> (I) new ReminderRemoveInquiry();
            case WORKOUT_ADD -> (I) new WorkoutAddInquiry();
            case WORKOUT_REMOVE -> (I) new WorkoutRemoveInquiry();
            case START -> (I) new StartInquiry();
            case STOP -> (I) new StopInquiry();
            case KEYBOARD -> (I) new KeyboardInquiry();
            case UNKNOWN -> (I) new UnknownInquiry();
            case UNKNOWN_USER -> (I) new UnknownUserInquiry();
            default -> throw new TelegramBotException("Inquiry not defined!");
        };
    }

    public I getInquiry(String command) {
        return getInquiry(InquiryType.getFromCommand(command));
    }
}
