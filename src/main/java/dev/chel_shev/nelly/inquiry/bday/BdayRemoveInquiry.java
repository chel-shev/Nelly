package dev.chel_shev.nelly.inquiry.bday;

import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.CalendarService;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@InquiryId(type = InquiryType.BDAY, command = "/bday_remove")
public class BdayRemoveInquiry extends Inquiry {

    private final BdayService service;
    private final CalendarService calendarService;

    protected BdayRemoveInquiry(BdayService service, CalendarService calendarService) {
        this.service = service;
        this.calendarService = calendarService;
    }

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("День Рождения удален!");
            add("Ну поссорились, так и скажи..");
            add("Мне тоже не нравился...");
        }};
        Set<String> secondLevel = new HashSet<>() {{
            add("Дня Рождения не найден!");
            add("Не были знакомы, и удалять нечего..");
            add("Ты ошибся, я о таких даже не слышала.");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
    }

    @Override
    public InquiryAnswer logic() {
        if (!validationArgs(1, ">=")) {
            throw new TelegramBotException("Неверное кол-во аргументов :(");
        }
        String name = getArgFromMassage(0);
        done();
        if (service.isExist(name)) {
            calendarService.removeEvent(name, getUser());
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.FIRST, this), KeyboardType.NONE);
        } else {
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.SECOND, this), KeyboardType.NONE);
        }
    }
}
