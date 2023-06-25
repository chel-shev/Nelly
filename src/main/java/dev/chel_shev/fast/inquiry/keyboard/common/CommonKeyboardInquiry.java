package dev.chel_shev.fast.inquiry.keyboard.common;

import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.keyboard.bday.BdayKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.finance.FinanceKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.subscription.SubscriptionKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.workout.WorkoutKeyboardInquiry;
import dev.chel_shev.fast.type.FastInquiryType;

@FastInquiryId(command = "/common", type = FastInquiryType.KEYBOARD, label = "Общая", buttons = {BdayKeyboardInquiry.class, FinanceKeyboardInquiry.class, WorkoutKeyboardInquiry.class, SubscriptionKeyboardInquiry.class})
public class CommonKeyboardInquiry extends FastInquiry {
    @Override
    public String toString() {
        return super.toString() +
                '}';
    }
}