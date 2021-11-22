package dev.chel_shev.nelly.inquiry.handler.bday;

import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.inquiry.handler.InquiryHandler;
import dev.chel_shev.nelly.inquiry.prototype.bday.BdayInquiry;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.CalendarService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.util.DateTimeUtils;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static dev.chel_shev.nelly.inquiry.InquiryUtils.getLastArgsPast;
import static dev.chel_shev.nelly.inquiry.InquiryUtils.validationArgs;
import static dev.chel_shev.nelly.type.KeyboardType.BDAY;
import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;

@Service
@Slf4j
@RequiredArgsConstructor
public class BdayHandler extends InquiryHandler<BdayInquiry> {

    private final BdayService service;
    private final CalendarService calendarService;

    @Override
    public BdayInquiry executionLogic(BdayInquiry i) {
        if (calendarService.isExist(i.getName(), i.getBdayDate(), i.getUser())) {
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.SECOND, i));
        } else {
            BdayEntity save = service.save(new BdayEntity(i.getName(), i.getBdayDate()));
            List<CalendarEntity> calendarEntities = service.getCalendarEntities(save, i.getUser());
            calendarService.addEvents(calendarEntities);
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.FIRST, i));
        }
        i.setClosed(true);
        i.setKeyboardType(BDAY);
        return i;
    }

    public BdayInquiry cancel(BdayInquiry i) {
        i.setClosed(true);
        i.setAnswerMessage("Действие отменено!");
        i.setKeyboardType(BDAY);
        save(i.getEntity());
        log.info("CANCEL Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return i;
    }

    @Override
    public BdayInquiry preparationLogic(BdayInquiry i, Message message) {
        if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.THIRD, i));
            i.setKeyboardType(CANCEL);
        } else {
            i.setMessage(TelegramBotUtils.getArgs(message.getText()));
            i.setName(getLastArgsPast(i, 0));
            i.setBdayDate(DateTimeUtils.tryToParse(i.getArgFromMassage(0), i.getUser()));
        }
        return i;
    }
}