package dev.chel_shev.nelly.inquiry.command;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.InquiryType;
import dev.chel_shev.nelly.keyboard.KeyboardType;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.CommandService;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.service.UserService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@InquiryId(type = InquiryType.COMMAND, command = "/start")
public class StartInquiry extends Inquiry {

    private final UserService userService;

    public StartInquiry(UserService userService, CommandService commandService, AnswerService answerService, InquiryService inquiryService) {
        super(inquiryService, commandService, answerService);
        this.userService = userService;
    }

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Ну привет!");
            add("Привет :)");
            add("Попробуем подружиться..");
        }};
        Set<String> secondLevel = new HashSet<>() {{
            add("Ты уже в списке!");
            add("Как ты с таким разумом вообще живешь, /start это для новеньких...");
            add("Я промолчу, хотя..., мы уже знакомы.");
            add("У меня отличная память, два раза не знакомлюсь");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
        getAnswer().put(CommandLevel.SECOND, secondLevel);
    }

    @Override
    public InquiryAnswer logic() {
        if (!userService.isExist(getUser().getChatId())) {
            userService.save(getUser());
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.FIRST, this), KeyboardType.NONE);
        } else {
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.SECOND, this), KeyboardType.NONE);
        }
    }
}