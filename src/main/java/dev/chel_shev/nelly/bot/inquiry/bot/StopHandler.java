package dev.chel_shev.nelly.bot.inquiry.bot;

import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StopHandler extends InquiryHandler<StopInquiry> {

    private final UserService userService;
    private final StopConfig stopConfig;

    @Override
    public void executionLogic(StopInquiry i) {
        userService.delete(i.getUser());
        i.setAnswerMessage(aSer.generateAnswer(CommandLevel.FIRST, stopConfig));
        i.setKeyboardType(KeyboardType.NONE);
    }
}