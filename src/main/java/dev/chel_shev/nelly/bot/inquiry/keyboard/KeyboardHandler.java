package dev.chel_shev.nelly.bot.inquiry.keyboard;

import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dev.chel_shev.nelly.type.KeyboardType.getFromLabel;

@Component
@RequiredArgsConstructor
public class KeyboardHandler extends InquiryHandler<KeyboardInquiry> {

    @Override
    public void executionLogic(KeyboardInquiry i) {
        switch (getFromLabel(i.getMessage())) {
            case BDAY -> {
                i.setAnswerMessage("Ну давай изменим пару дат :)");
                i.setKeyboardType(KeyboardType.BDAY);
            }
            case FINANCE -> {
                i.setAnswerMessage("Пришло время подсчитывать финансы :)");
                i.setKeyboardType(KeyboardType.FINANCE);
            }
            case REMINDER -> {
                i.setAnswerMessage("Наведем порядок в напоминаниях!");
                i.setKeyboardType(KeyboardType.REMINDER);
            }
            case BACK -> {
                i.setAnswerMessage("Закончим с этим..");
                i.setKeyboardType(KeyboardType.COMMON);
            }
            case WORKOUT -> {
                i.setAnswerMessage("Что там по спорту?");
                i.setKeyboardType(KeyboardType.WORKOUT);
            }
            default -> {
                i.setAnswerMessage("Не поняла тебя...");
                i.setKeyboardType(KeyboardType.COMMON);
            }
        }
        i.setClosed(true);
    }
}
