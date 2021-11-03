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

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@InquiryId(type = InquiryType.BDAY, command = "Добавить ДР")
public class BdayInquiry extends Inquiry {

    private final BdayService service;
    private final CalendarService calendarService;
    private String name;
    private LocalDateTime date;


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
        Set<String> thirdLevel = new HashSet<>() {{
            add("Введите 'дата (в формате `дд.мм.гггг` или `дд.мм`) имя', чтоб добавить день рождения!");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
        getAnswer().put(CommandLevel.THIRD, thirdLevel);
    }

    @Override
    public InquiryAnswer logic() {
        if (validationArgs(2, "=="))
            throw new TelegramBotException(getUser(), "Неверное кол-во аргументов :(");
        else if (validationArgs(1, "=="))
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.THIRD, this), KeyboardType.NONE);
        return setData();
    }

    public InquiryAnswer setData() {
        this.name = getLastArgsPast(0);
        this.date = DateTimeUtils.tryToParse(getArgFromMassage(0), getUser());
        return saveBday();
    }

    private InquiryAnswer saveBday(){
        if (calendarService.isExist(name, date, getUser())) {
            done();
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.SECOND, this), KeyboardType.NONE);
        } else {
            BdayEntity save = service.save(new BdayEntity(name, date));
            List<CalendarEntity> calendarEntities = service.getCalendarEntities(save, getUser());
            calendarService.addEvents(calendarEntities);
            done();
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.FIRST, this), KeyboardType.NONE);
        }
    }

    @Override
    public boolean isReadyForProcess() {
        return !isNullOrEmpty(name) && date != null;
    }
}