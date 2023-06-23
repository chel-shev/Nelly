package dev.chel_shev.fast.inquiry.keyboard.english;

import dev.chel_shev.fast.entity.WordEntity;
import dev.chel_shev.fast.entity.event.FastWordEventEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.language.FastLanguageEvent;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.service.FastEventService;
import dev.chel_shev.fast.service.FastUserSubscriptionService;
import dev.chel_shev.fast.service.LanguageService;
import dev.chel_shev.fast.type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LanguageKeyboardHandler extends FastInquiryHandler<LanguageKeyboardInquiry> {

    private final LanguageKeyboardConfig englishKeyboardConfig;
    private final FastUserSubscriptionService subscriptionService;
    private final FastEventService<FastLanguageEvent> eventService;
    private final LanguageService service;

    @Override
    protected void executionLogic(LanguageKeyboardInquiry i, Message m) {
        FastUserSubscriptionEntity subscription = subscriptionService.getSubscription(i.getUser(), i.getCommand(), SubscriptionType.MAIN);
        if(subscription.getStatus() == SubscriptionStatusType.ACTIVE) {
            subscription.setStatus(SubscriptionStatusType.PAUSE);
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, englishKeyboardConfig));
        }else {
            subscription.setStatus(SubscriptionStatusType.ACTIVE);
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.SECOND, englishKeyboardConfig));
        }
        subscriptionService.save(subscription);
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtons(keyboardService.getButtons(i.getUser()));
//        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, englishKeyboardConfig));
        closeLastInquiry(m);
        i.setClosed(true);
    }

    @Override
    public void init(FastInquiry i) {
        WordEntity unknownWord = service.getUnknownWord(i.getUser());
        LocalDate eventDate = LocalDate.now().plusDays(1);
        LocalDateTime eventDateTime = LocalDateTime.of(eventDate, FastStudyTimeType.T_10_15.getTimeEvent());
        FastWordEventEntity entity = new FastWordEventEntity(i.getCommand(), FastPeriodType.ONCE, eventDateTime, subscriptionService.getSubscription(i.getUser(), i.getCommand(), SubscriptionType.MAIN), unknownWord, FastStudyTimelineType.T_0, FastStudyTimeType.T_10_15);
        eventService.save(entity);
        service.saveNewUserWord(eventDateTime, unknownWord, i.getUser());
    }
}