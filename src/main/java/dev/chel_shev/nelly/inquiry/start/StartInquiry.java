package dev.chel_shev.nelly.inquiry.start;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@InquiryId(type = InquiryType.START, command = "/start")
public class StartInquiry extends Inquiry {

    private final UserService userService;

    public StartInquiry(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Ну привет!");
            add("Привет :)");
            add("Попробуем подружиться..");
            add("Рада встрече!");
            add("Здравствуй!");
            add("Добро пожаловать!");
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
        done();
        if (userService.isExist(getUser().getChatId())) {
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.SECOND, this), KeyboardType.COMMON);
        } else {
            userService.save(getUser());
            return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.FIRST, this), KeyboardType.COMMON);
        }
    }
}