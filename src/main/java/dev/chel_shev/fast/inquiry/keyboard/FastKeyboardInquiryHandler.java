package dev.chel_shev.fast.inquiry.keyboard;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public abstract class FastKeyboardInquiryHandler extends FastInquiryHandler<FastKeyboardInquiry> {

    @Override
    public void executionLogic(FastKeyboardInquiry i, Message message) {
//        switch (getFromLabel(i.getMessage())) {
//            case BDAY -> {
//                i.setAnswerMessage("Ну давай изменим пару дат :)");
//                i.setKeyboardType(KeyboardType.BDAY);
//            }
//            case FINANCE -> {
//                i.setAnswerMessage("Пришло время подсчитывать финансы :)");
//                i.setKeyboardType(KeyboardType.FINANCE);
//            }
//            case REMINDER -> {
//                i.setAnswerMessage("Наведем порядок в напоминаниях!");
//                i.setKeyboardType(KeyboardType.REMINDER);
//            }
//            case BACK -> {
//                i.setAnswerMessage("Закончим с этим..");
//                i.setKeyboardType(KeyboardType.COMMON);
//            }
//            case WORKOUT -> {
//                i.setAnswerMessage("Что там по спорту?");
//                i.setKeyboardType(KeyboardType.WORKOUT);
//            }
//            default -> {
//                i.setAnswerMessage("Не поняла тебя...");
//                i.setKeyboardType(KeyboardType.COMMON);
//            }
//        }
        i.setClosed(true);
    }
}
