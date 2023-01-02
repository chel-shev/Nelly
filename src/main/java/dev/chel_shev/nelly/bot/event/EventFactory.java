package dev.chel_shev.nelly.bot.event;

import dev.chel_shev.nelly.bot.event.bday.BdayEvent;
import dev.chel_shev.nelly.bot.event.workout.WorkoutEvent;
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
import dev.chel_shev.nelly.bot.inquiry.workout.WorkoutAddInquiry;
import dev.chel_shev.nelly.bot.inquiry.workout.WorkoutRemoveInquiry;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.type.EventType;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static dev.chel_shev.nelly.type.EventType.BDAY;
import static dev.chel_shev.nelly.type.EventType.WORKOUT;

@Component
@Scope("singleton")
@RequiredArgsConstructor
public class EventFactory<E extends Event> {

    @SuppressWarnings("unchecked")
    public E getEvent(EventType type) {
        return switch (type) {
            case BDAY -> (E) new BdayEvent();
            case WORKOUT -> (E) new WorkoutEvent();
        };
    }

    public E getEvent(String command) {
        return getEvent(EventType.getFromCommand(command));
    }
}
