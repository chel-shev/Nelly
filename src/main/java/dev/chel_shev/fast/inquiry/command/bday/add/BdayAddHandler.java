package dev.chel_shev.fast.inquiry.command.bday.add;

import dev.chel_shev.fast.entity.event.FastBdayEventEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiryHandler;
import dev.chel_shev.fast.inquiry.keyboard.bday.BdayKeyboardInquiry;
import dev.chel_shev.fast.inquiry.keyboard.cancel.CancelKeyboardInquiry;
import dev.chel_shev.fast.service.FastBdayEventService;
import dev.chel_shev.fast.service.FastCommandService;
import dev.chel_shev.fast.service.FastEventService;
import dev.chel_shev.fast.service.FastUserSubscriptionService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.type.SubscriptionType;
import dev.chel_shev.nelly.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;

import static dev.chel_shev.fast.FastUtils.getLastArgsPast;
import static java.util.Arrays.asList;

@Service
@Slf4j
@RequiredArgsConstructor
public class BdayAddHandler extends FastCommandInquiryHandler<BdayAddInquiry> {

    private final FastBdayEventService service;
    private final FastEventService<? extends FastEvent> eventService;
    private final BdayAddConfig bdayAddConfig;
    private final FastUserSubscriptionService subscriptionService;
    private final FastCommandService commandService;

    @Override
    public void executionLogic(BdayAddInquiry i, Message message) {
        if (eventService.isExist(i.getName(), i.getUser(), i.getBdayDate())) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.SECOND, bdayAddConfig));
        } else {
            LocalDateTime now = LocalDateTime.now();
            FastBdayEventEntity bdayEvent;
            if (i.getBdayDate().withYear(now.getYear()).isAfter(now))
                bdayEvent = new FastBdayEventEntity(i.getName(), i.getBdayDate(), i.getCommand(), i.getBdayDate().withYear(now.getYear()).withHour(8).withMinute(0).withSecond(0), subscriptionService.getSubscription(i.getUser(), commandService.getCommand("/bday"), SubscriptionType.MAIN));
            else
                bdayEvent = new FastBdayEventEntity(i.getName(), i.getBdayDate(), i.getCommand(), i.getBdayDate().withYear(now.getYear() + 1).withHour(8).withMinute(0).withSecond(0),  subscriptionService.getSubscription(i.getUser(), commandService.getCommand("/bday"), SubscriptionType.MAIN));
            eventService.save(bdayEvent);
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, bdayAddConfig));
        }
        i.setClosed(true);
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtons(keyboardService.getButtons(BdayKeyboardInquiry.class));
    }

    public BdayAddInquiry cancel(BdayAddInquiry i) {
        super.cancel(i);
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtons(asList("", ""));
        return i;
    }

    @Override
    public void preparationLogic(BdayAddInquiry i, Message message) {
        if (fastUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.THIRD, bdayAddConfig));
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtons(keyboardService.getButton(CancelKeyboardInquiry.class));
        } else {
            i.setMessage(fastUtils.getArgs(message.getText()));
            i.setName(getLastArgsPast(i, 0));
            i.setBdayDate(DateTimeUtils.tryToParse(i.getArgFromMassage(message.getText(), 0)));
        }
    }
}