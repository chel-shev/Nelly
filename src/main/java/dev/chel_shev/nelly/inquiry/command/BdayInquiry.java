package dev.chel_shev.nelly.inquiry.command;

import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.InquiryType;
import dev.chel_shev.nelly.keyboard.KeyboardType;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.CommandService;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.util.DateTimeUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@InquiryId(type = InquiryType.COMMAND, command = "/bday")
public class BdayInquiry extends Inquiry {

    private final BdayService service;

    protected BdayInquiry(InquiryService inquiryService, CommandService commandService, AnswerService answerService, BdayService service) {
        super(inquiryService, commandService, answerService);
        this.service = service;
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
            add("День Рождения удален!");
            add("Ну поссорились, так и скажи..");
            add("Мне тоже не нравился...");
        }};
        Set<String> fourthLevel = new HashSet<>() {{
            add("Дня Рождения не найден!");
            add("Не были знакомы, и удалять нечего..");
            add("Ты ошибся, я о таких даже не слышала.");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
        getAnswer().put(CommandLevel.THIRD, thirdLevel);
        getAnswer().put(CommandLevel.FOURTH, fourthLevel);
    }

    @Override
    public InquiryAnswer logic() {
        validationArgs(2, ">=");
        if (getArgFromMassage(0).equals("remove")) {
            return removeBday(getArgFromMassage(1));
        } else {
            return addBday(getLastArgsPast(0), DateTimeUtils.tryToParse(getArgFromMassage(0)));
        }
    }

    private InquiryAnswer removeBday(String name) {
        if (service.isExist(name)) {
            service.remove(name);
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.THIRD, this), KeyboardType.NONE);
        } else {
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.FOURTH, this), KeyboardType.NONE);
        }
    }

    private InquiryAnswer addBday(String name, LocalDate date) {
        if (service.isExist(name, date)) {
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.SECOND, this), KeyboardType.NONE);
        } else {
            service.save(new BdayEntity(null, name, date, getUser()));
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.FIRST, this), KeyboardType.NONE);
        }
    }
}