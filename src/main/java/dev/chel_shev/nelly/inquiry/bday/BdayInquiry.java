package dev.chel_shev.nelly.inquiry.bday;

import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.CalendarService;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.DateTimeUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@InquiryId(type = InquiryType.BDAY, command = "/bday")
public class BdayInquiry extends Inquiry {

    private final BdayService service;
    private final CalendarService calendarService;

    protected BdayInquiry(BdayService service, CalendarService calendarService) {
        this.service = service;
        this.calendarService = calendarService;
    }

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("День Рождения добавлен!");
            add("Все прошло успешно, человечек в списке:)");
            add("Напомню при необходимости поздавить!");
        }};
        Set<String> secondLevel = new HashSet<>() {{
            add("День Рождения уже существует!");
            add("Такое уже знаем :)");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
    }

    @Override
    public InquiryAnswer logic() {
        if (!validationArgs(2, ">=")) {
            throw new TelegramBotException("Неверное кол-во аргументов :(");
        }
        String name = getLastArgsPast(0);
        LocalDateTime date = DateTimeUtils.tryToParse(getArgFromMassage(0));
        done();
        if (service.isExist(name, date, getUser())) {
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.SECOND, this), KeyboardType.NONE);
        } else {
            BdayEntity save = service.save(new BdayEntity(name, date));
            List<CalendarEntity> calendarEntities = service.getCalendarEntities(save, getUser());
            calendarService.addEvents(calendarEntities);
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.FIRST, this), KeyboardType.NONE);
        }
    }
}