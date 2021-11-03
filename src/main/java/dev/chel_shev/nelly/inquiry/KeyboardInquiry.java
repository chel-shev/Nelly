package dev.chel_shev.nelly.inquiry;

import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import org.springframework.stereotype.Component;

@Component
@InquiryId(type = InquiryType.KEYBOARD, command = "/keyboard")
public class KeyboardInquiry extends Inquiry {

    @Override
    public void initAnswers() {

    }

    @Override
    public InquiryAnswer logic() {
        done();
        return switch (getMessage()) {
            case "ДР" -> new InquiryAnswer(getUser(), "Ну давай изменим пару дат :)", KeyboardType.BDAY);
            case "Финансы" -> new InquiryAnswer(getUser(), "Пришло время подсчитывать финансы :)", KeyboardType.FINANCE);
            case "Назад" -> new InquiryAnswer(getUser(), "Закончим с этим..", KeyboardType.COMMON);
            default -> null;
        };
    }
}
