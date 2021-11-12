package dev.chel_shev.nelly.inquiry.prototype.stop;

import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope("prototype")
@InquiryId(type = InquiryType.STOP, command = "/stop")
public class StopInquiry extends Inquiry {

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
}