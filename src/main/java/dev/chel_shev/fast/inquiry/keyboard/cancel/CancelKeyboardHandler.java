package dev.chel_shev.fast.inquiry.keyboard.cancel;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.inquiry.keyboard.common.CommonKeyboardInquiry;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelKeyboardHandler extends FastInquiryHandler<CancelKeyboardInquiry> {

    private final CancelKeyboardConfig cancelKeyboardConfig;
    @Override
    public void executionLogic(CancelKeyboardInquiry i) {
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(keyboardService.getButtons(i.getUser()));
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, cancelKeyboardConfig));
    }
}