package dev.chel_shev.nelly.inquiry.command;

import dev.chel_shev.nelly.inquiry.*;
import dev.chel_shev.nelly.keyboard.KeyboardType;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.CommandService;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@InquiryId(type = InquiryType.COMMAND, command = "/stop")
public class StopInquiry extends Inquiry {

    private final UserService userService;

    public StopInquiry(UserService userService, CommandService commandService, AnswerService answerService, InquiryService inquiryService) {
        super(inquiryService, commandService, answerService);
        this.userService = userService;
    }

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Прощай!");
            add("ВСЕ-ГО ХО-РО-ШЕ-ГО");
            add("Ты меня огорчаешь, удачи..");
            add("Удачи!");
        }};
        getAnswer().put(CommandLevel.FIRST, firstLevel);
    }

    @Override
    public InquiryAnswer logic() {
        userService.delete(getUser());
        return new InquiryAnswer(getUser(), answerService.generateAnswer(CommandLevel.FIRST, this), KeyboardType.NONE);
    }

    @Override
    public InquiryAnswer process() {
        return logic();
    }
}