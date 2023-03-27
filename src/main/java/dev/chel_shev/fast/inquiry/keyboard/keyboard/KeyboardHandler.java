package dev.chel_shev.fast.inquiry.keyboard.keyboard;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeyboardHandler extends FastInquiryHandler<KeyboardInquiry> {

//    @Override
//    public void executionLogic(KeyboardInquiry i) {
//        switch (getFromLabel(i.getMessage())) {
//            case REMINDER -> {
//                i.setAnswerMessage("Наведем порядок в напоминаниях!");
//                i.setKeyboardType(KeyboardType.REMINDER);
//            }
//            case BACK -> {
//                i.setAnswerMessage("Закончим с этим..");
//                i.setKeyboardType(KeyboardType.COMMON);
//            }
//            default -> {
//                i.setAnswerMessage("Не поняла тебя...");
//                i.setKeyboardType(KeyboardType.COMMON);
//            }
//        }
//        i.setClosed(true);
//    }
}
