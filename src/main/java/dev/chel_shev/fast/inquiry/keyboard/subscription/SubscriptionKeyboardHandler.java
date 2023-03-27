package dev.chel_shev.fast.inquiry.keyboard.subscription;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionKeyboardHandler extends FastInquiryHandler<SubscriptionKeyboardInquiry> {

    private final SubscriptionKeyboardConfig subscriptionKeyboardConfig;

    @Override
    public void executionLogic(SubscriptionKeyboardInquiry i) {
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(keyboardService.getButtons(SubscriptionKeyboardInquiry.class));
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, subscriptionKeyboardConfig));
    }
}