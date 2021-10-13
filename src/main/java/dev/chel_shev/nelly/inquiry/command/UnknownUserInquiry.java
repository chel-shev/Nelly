package dev.chel_shev.nelly.inquiry.command;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.CommandService;
import dev.chel_shev.nelly.service.InquiryService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@InquiryId(type = InquiryType.NONE, command = "/unknown_user")
public class UnknownUserInquiry extends Inquiry {

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Мы вроде не знакомы, или я ошибаюсь?");
            add("Попробуй начать со /start, ты слишком быстрый..");
            add("Я не знакомлюсь с неизвестных команд :)");
            add("Попробуйте еще раз, но у вас ничего не получится.");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
    }

    @Override
    public InquiryAnswer logic() {
        return null;
    }
}