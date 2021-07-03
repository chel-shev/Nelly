package dev.chel_shev.nelly.inquiry.command;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.InquiryType;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.CommandService;
import dev.chel_shev.nelly.service.InquiryService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@InquiryId(type = InquiryType.COMMAND, command = "/unknown")
public class UnknownInquiry extends Inquiry {

    protected UnknownInquiry(InquiryService inquiryService, AnswerService answerService, CommandService commandService) {
        super(inquiryService, commandService, answerService);
    }

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Давай удачи, я на знаю, что ты тут мне пишешь..");
            add("Я еще не настолько умна, чтоб знать придуманные тобой команды..");
            add("Такое не знаем...");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
    }

    @Override
    public InquiryAnswer logic() {
        return null;
    }
}
