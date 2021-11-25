package dev.chel_shev.nelly.inquiry.bot;

import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope("prototype")
@InquiryId(type = InquiryType.START)
public class StartInquiry extends Inquiry {

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
}