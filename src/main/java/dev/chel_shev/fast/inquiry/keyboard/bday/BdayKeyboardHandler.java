package dev.chel_shev.fast.inquiry.keyboard.bday;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BdayKeyboardHandler extends FastInquiryHandler<BdayKeyboardInquiry> {

    private final BdayKeyboardConfig bdayKeyboardConfig;
    @Override
    public void executionLogic(BdayKeyboardInquiry i) {
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(keyboardService.getButtons(BdayKeyboardInquiry.class));
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, bdayKeyboardConfig));
    }
}