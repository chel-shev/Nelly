package dev.chel_shev.nelly.inquiry.stop;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.CommandService;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.type.KeyboardType;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@InquiryId(type = InquiryType.STOP, command = "/stop")
public class StopInquiry extends Inquiry {

    private final UserService userService;

    public StopInquiry(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initAnswers() {
        Set<String> firstLevel = new HashSet<>() {{
            add("Прощай!");
            add("ВСЕ-ГО ХО-РО-ШЕ-ГО");
            add("Ты меня огорчаешь, удачи..");
            add("Удачи!");
            add("Ну и ладно, ну и пожалуйста..");
            add("Не очень-то и хотелось (:");
            add("Поматросил и бросил?");
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