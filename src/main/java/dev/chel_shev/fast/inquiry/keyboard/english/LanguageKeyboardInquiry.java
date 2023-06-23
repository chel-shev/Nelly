package dev.chel_shev.fast.inquiry.keyboard.english;

import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.keyboard.bday.BdayKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.finance.FinanceKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.subscription.SubscriptionKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.workout.WorkoutKeyboardInquiry;
import dev.chel_shev.fast.type.FastInquiryType;

@FastInquiryId(command = "/language",
        label = "\uD83C\uDDEC\uD83C\uDDE7 Английский",
        type = FastInquiryType.KEYBOARD_SUBSCRIPTION,
        buttons = {
                BdayKeyboardInquiry.class,
                FinanceKeyboardInquiry.class,
                WorkoutKeyboardInquiry.class,
                SubscriptionKeyboardInquiry.class
        })
public class LanguageKeyboardInquiry extends FastInquiry {
}