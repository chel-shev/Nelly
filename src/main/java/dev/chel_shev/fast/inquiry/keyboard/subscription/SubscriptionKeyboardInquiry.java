package dev.chel_shev.fast.inquiry.keyboard.subscription;

import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.inquiry.command.subscription.subscribe.SubscriptionSubscribeInquiry;
import dev.chel_shev.fast.inquiry.command.subscription.unsubscribe.SubscriptionUnsubscribeInquiry;
import dev.chel_shev.fast.inquiry.keyboard.FastKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.cancel.CancelKeyboardInquiry;
import dev.chel_shev.fast.type.FastInquiryType;

@FastInquiryId(command = "/subscription", buttons = {SubscriptionSubscribeInquiry.class, SubscriptionUnsubscribeInquiry.class, CancelKeyboardInquiry.class}, type = FastInquiryType.KEYBOARD, label = "Подписки")
public class SubscriptionKeyboardInquiry extends FastKeyboardInquiry {
}