package dev.chel_shev.nelly.inquiry.bday;

import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.CalendarService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.util.DateTimeUtils;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static dev.chel_shev.nelly.inquiry.utils.InquiryUtils.getLastArgsPast;
import static dev.chel_shev.nelly.type.KeyboardType.BDAY;
import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource(value = "classpath:message.yml", encoding = "UTF-8")
public class BdayAddHandler extends InquiryHandler<BdayAddInquiry> {

    private final BdayService service;
    private final CalendarService calendarService;
    private final BdayAddConfig bdayAddConfig;

    @Override
    public BdayAddInquiry executionLogic(BdayAddInquiry i) {
        if (calendarService.isExist(i.getName(), i.getBdayDate(), i.getUser())) {
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.SECOND, bdayAddConfig));
        } else {
            BdayEntity save = service.save(new BdayEntity(i.getName(), i.getBdayDate()));
            List<CalendarEntity> calendarEntities = service.getCalendarEntities(save, i.getUser());
            calendarService.addEvents(calendarEntities);
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.FIRST, bdayAddConfig));
        }
        i.setClosed(true);
        i.setKeyboardType(BDAY);
        return i;
    }

    public BdayAddInquiry cancel(BdayAddInquiry i) {
        i.setClosed(true);
        i.setAnswerMessage("Действие отменено!");
        i.setKeyboardType(BDAY);
        save(i.getEntity());
        log.info("CANCEL Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return i;
    }

    @Override
    public BdayAddInquiry preparationLogic(BdayAddInquiry i, Message message) {
        if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.THIRD, bdayAddConfig));
            i.setKeyboardType(CANCEL);
        } else {
            i.setMessage(TelegramBotUtils.getArgs(message.getText()));
            i.setName(getLastArgsPast(i, 0));
            i.setBdayDate(DateTimeUtils.tryToParse(i.getArgFromMassage(0), i.getUser()));
        }
        return i;
    }
}