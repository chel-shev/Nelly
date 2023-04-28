package dev.chel_shev.fast.inquiry.keyboard.cancel;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.service.FastInquiryService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class CancelKeyboardHandler extends FastInquiryHandler<CancelKeyboardInquiry> {

    private final CancelKeyboardConfig cancelKeyboardConfig;

    @Override
    public void executionLogic(CancelKeyboardInquiry i, Message m) {
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtons(keyboardService.getButtons(i.getUser()));
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, cancelKeyboardConfig));
        closeLastInquiry(m);
        i.setClosed(true);
    }
}