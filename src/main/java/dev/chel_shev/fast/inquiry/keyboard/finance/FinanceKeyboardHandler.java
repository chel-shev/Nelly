package dev.chel_shev.fast.inquiry.keyboard.finance;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class FinanceKeyboardHandler extends FastInquiryHandler<FinanceKeyboardInquiry> {

    private final FinanceKeyboardConfig bdayKeyboardConfig;
    @Override
    public void executionLogic(FinanceKeyboardInquiry i, Message message) {
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtons(keyboardService.getButtons(FinanceKeyboardInquiry.class));
//        i.setAnswerMessage(answerService.generateAnswer());
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, bdayKeyboardConfig));
    }

//    @Override
//    public void executionLogic(KeyboardInquiry i) {
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
//        i.setClosed(true);
//    }
}
