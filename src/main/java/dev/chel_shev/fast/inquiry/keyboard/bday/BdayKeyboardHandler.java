package dev.chel_shev.fast.inquiry.keyboard.bday;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class BdayKeyboardHandler extends FastInquiryHandler<BdayKeyboardInquiry> {

    private final BdayKeyboardConfig bdayKeyboardConfig;
    @Override
    public void executionLogic(BdayKeyboardInquiry i, Message message) {
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtons(keyboardService.getButtons(BdayKeyboardInquiry.class));
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, bdayKeyboardConfig));
    }
}