package dev.chel_shev.nelly.inquiry.bot;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope("prototype")
@InquiryId(type = InquiryType.NONE)
public class UnknownInquiry extends Inquiry {

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Давай удачи, я на знаю, что ты тут мне пишешь..");
            add("Я еще не настолько умна, чтоб знать придуманные тобой команды..");
            add("Такое не знаем...");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
    }
}
