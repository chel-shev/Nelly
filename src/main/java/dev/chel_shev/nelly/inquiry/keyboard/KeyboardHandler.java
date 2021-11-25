package dev.chel_shev.nelly.inquiry.keyboard;

import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.KeyboardKeyType.getFromLabel;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class KeyboardHandler extends InquiryHandler<KeyboardInquiry> {

    @Override
    public KeyboardInquiry executionLogic(KeyboardInquiry inquiry) {
        switch (getFromLabel(inquiry.getMessage())) {
            case BDAY_KEY -> {
                inquiry.setAnswerMessage("Ну давай изменим пару дат :)");
                inquiry.setKeyboardType(KeyboardType.BDAY);
            }
            case FINANCE_KEY -> {
                inquiry.setAnswerMessage("Пришло время подсчитывать финансы :)");
                inquiry.setKeyboardType(KeyboardType.FINANCE);
            }
            case REMINDER -> {
                inquiry.setAnswerMessage("Наведем порядок в напоминаниях!");
                inquiry.setKeyboardType(KeyboardType.REMINDER);
            }
            case BACK_KEY -> {
                inquiry.setAnswerMessage("Закончим с этим..");
                inquiry.setKeyboardType(KeyboardType.COMMON);
            }
            default -> {
                inquiry.setAnswerMessage("Не поняла тебя...");
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
