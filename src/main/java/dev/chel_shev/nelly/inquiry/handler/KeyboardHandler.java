package dev.chel_shev.nelly.inquiry.handler;

import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.prototype.KeyboardInquiry;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@InquiryId(type = InquiryType.KEYBOARD, command = "/keyboard")
public class KeyboardHandler extends InquiryHandler<KeyboardInquiry> {

    @Override
    public KeyboardInquiry executionLogic(KeyboardInquiry inquiry) {
        switch (inquiry.getMessage()) {
            case "ДР" -> {
                inquiry.setAnswerMessage("Ну давай изменим пару дат :)");
                inquiry.setKeyboardType(KeyboardType.BDAY);
            }
            case "Финансы" -> {
                inquiry.setAnswerMessage("Пришло время подсчитывать финансы :)");
                inquiry.setKeyboardType(KeyboardType.FINANCE);
            }
            case "Назад" -> {
                inquiry.setAnswerMessage("Закончим с этим..");
                inquiry.setKeyboardType(KeyboardType.COMMON);
            }
        }
        inquiry.setClosed(true);
        return inquiry;
    }

    @Override
    public KeyboardInquiry preparationLogic(KeyboardInquiry keyboardInquiry, Message message) {
        return null;
    }
}
