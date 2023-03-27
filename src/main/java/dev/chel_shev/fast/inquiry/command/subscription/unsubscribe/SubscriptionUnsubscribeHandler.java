package dev.chel_shev.fast.inquiry.command.subscription.unsubscribe;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.inquiry.command.subscription.subscribe.SubscriptionSubscribeConfig;
import dev.chel_shev.fast.inquiry.command.subscription.subscribe.SubscriptionSubscribeInquiry;
import dev.chel_shev.fast.inquiry.keyboard.subscription.SubscriptionKeyboardInquiry;
import dev.chel_shev.fast.service.FastCommandService;
import dev.chel_shev.fast.service.FastUserSubscriptionService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

import static java.util.Objects.isNull;

@Component
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class SubscriptionUnsubscribeHandler extends FastInquiryHandler<SubscriptionUnsubscribeInquiry> {

    private final SubscriptionUnsubscribeConfig subscriptionUnsubscribeConfig;
    private final FastUserSubscriptionService subscriptionService;
    private final FastCommandService commandService;

    @Override
    protected void preparationLogic(SubscriptionUnsubscribeInquiry i, Message message) {
        if (i.getMessage().isEmpty()) {
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtonList(subscriptionService.getSubscriptions(i.getUser()));
            i.setAnswerMessage("Подписки:");
        }
    }

    @Override
    public void inlineExecutionLogic(SubscriptionUnsubscribeInquiry i, CallbackQuery callbackQuery) {
        if (!isNull(i.getSubscription())) {
            subscriptionService.removeSubscription(i.getUser(), i.getSubscription());
            i.setAnswerMessage("Подписка удалена!");
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtonList(keyboardService.getButtons(SubscriptionKeyboardInquiry.class));
            i.setClosed(true);
        }
    }

    @Override
    public void inlinePreparationLogic(SubscriptionUnsubscribeInquiry i, CallbackQuery callbackQuery) {
        if(!callbackQuery.getData().isEmpty()) {
            FastCommandEntity commandByLabel = commandService.getCommandByLabel(callbackQuery.getData());
            i.setSubscription(commandByLabel);
        }
    }
}