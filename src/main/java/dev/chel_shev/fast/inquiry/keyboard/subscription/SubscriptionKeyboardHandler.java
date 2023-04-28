package dev.chel_shev.fast.inquiry.keyboard.subscription;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class SubscriptionKeyboardHandler extends FastInquiryHandler<SubscriptionKeyboardInquiry> {

    private final SubscriptionKeyboardConfig subscriptionKeyboardConfig;

    @Override
    public void executionLogic(SubscriptionKeyboardInquiry i, Message message) {
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtons(keyboardService.getButtons(SubscriptionKeyboardInquiry.class));
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, subscriptionKeyboardConfig));
    }
}