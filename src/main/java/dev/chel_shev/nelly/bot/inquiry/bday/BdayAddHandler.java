package dev.chel_shev.nelly.bot.inquiry.bday;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.EventService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.util.DateTimeUtils;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;

import static dev.chel_shev.nelly.bot.utils.InquiryUtils.getLastArgsPast;
import static dev.chel_shev.nelly.type.KeyboardType.BDAY;
import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource(value = "classpath:message.properties", encoding = "UTF-8")
public class BdayAddHandler extends InquiryHandler<BdayAddInquiry> {

    private final BdayService service;
    private final EventService<? extends Event> eventService;
    private final BdayAddConfig bdayAddConfig;

    @Override
    public void executionLogic(BdayAddInquiry i) {
        if (eventService.isExist(i.getName(), i.getBdayDate(), i.getUser())) {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.SECOND, bdayAddConfig));
        } else {
            LocalDateTime now = LocalDateTime.now();
            BdayEventEntity bdayEvent;
            if (i.getBdayDate().withYear(now.getYear()).isAfter(now))
                bdayEvent = new BdayEventEntity(i.getName(), i.getBdayDate(), i.getBdayDate().withYear(now.getYear()).withHour(8).withMinute(0).withSecond(0), service.getSubscription(i.getUser()));
            else
                bdayEvent = new BdayEventEntity(i.getName(), i.getBdayDate(), i.getBdayDate().withYear(now.getYear() + 1).withHour(8).withMinute(0).withSecond(0), service.getSubscription(i.getUser()));
            service.save(bdayEvent);
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.FIRST, bdayAddConfig));
        }
        i.setClosed(true);
        i.setKeyboardType(BDAY);
    }

    public BdayAddInquiry cancel(BdayAddInquiry i) {
        super.cancel(i);
        i.setKeyboardType(BDAY);
        return i;
    }

    @Override
    public void preparationLogic(BdayAddInquiry i, Message message) {
        if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.THIRD, bdayAddConfig));
            i.setKeyboardType(CANCEL);
        } else {
            i.setMessage(TelegramBotUtils.getArgs(message.getText()));
            i.setName(getLastArgsPast(i, 0));
            i.setBdayDate(DateTimeUtils.tryToParse(i.getArgFromMassage(0), i.getUser()));
        }
    }
}