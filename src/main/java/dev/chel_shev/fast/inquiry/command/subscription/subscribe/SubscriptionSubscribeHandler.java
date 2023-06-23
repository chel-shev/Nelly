package dev.chel_shev.fast.inquiry.command.subscription.subscribe;

import dev.chel_shev.fast.FastBot;
import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.inquiry.FastInquiryHandlerFactory;
import dev.chel_shev.fast.inquiry.keyboard.subscription.SubscriptionKeyboardInquiry;
import dev.chel_shev.fast.service.FastCommandService;
import dev.chel_shev.fast.service.FastUserSubscriptionService;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.type.SubscriptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static java.util.Objects.isNull;

@Component
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class SubscriptionSubscribeHandler extends FastInquiryHandler<SubscriptionSubscribeInquiry> {

    private final SubscriptionSubscribeConfig subscriptionSubscribeConfig;
    private final FastUserSubscriptionService subscriptionService;
    private final FastCommandService commandService;
    private final ApplicationContext applicationContext;

    @Override
    public void executionLogic(SubscriptionSubscribeInquiry i, Message message) {
//        if (service.isExist(i.getName())) {
//            calendarService.removeEvent(i.getName(), i.getUser());
//            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.FIRST, i));
//        } else {
//            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.SECOND, i));
//        }
//        i.setClosed(true);
//        i.setKeyboardType(BDAY);
    }

    @Override
    public void preparationLogic(SubscriptionSubscribeInquiry i, Message message) {
        if (i.getMessage().isEmpty()) {
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtons(subscriptionService.getAvailableSubscriptions(i.getUser()));
            i.setAnswerMessage("Доступные подписки:");
        }
    }

    @Override
    public void inlineExecutionLogic(SubscriptionSubscribeInquiry i, CallbackQuery callbackQuery) {
        if (!isNull(i.getSubscription())) {
            FastInquiryHandlerFactory inquiryHandlerFactory = (FastInquiryHandlerFactory) applicationContext.getBean("fastInquiryHandlerFactory");
            subscriptionService.addSubscription(i.getUser(), i.getSubscription(), i.getSubscription(), SubscriptionType.MAIN, i.getSubscription().getName());
            FastInquiry inquiry = inquiryService.getInquiryEmpty(i.getSubscription().getName());
            inquiry.init(i.getUser(), i.getSubscription());
            FastInquiryHandler<? extends FastInquiry> handler = inquiryHandlerFactory.getHandler(inquiry.getClass());
            handler.init(inquiry);
            i.setAnswerMessage("Подписка добавлена\\!");
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtons(keyboardService.getButtons(SubscriptionKeyboardInquiry.class));
            i.setClosed(true);
        }
    }

    @Override
    public void inlinePreparationLogic(SubscriptionSubscribeInquiry i, CallbackQuery callbackQuery) {
        if(!callbackQuery.getData().isEmpty()) {
            FastCommandEntity commandByLabel = commandService.getCommandByLabel(callbackQuery.getData());
            i.setSubscription(commandByLabel);
        }
    }
}