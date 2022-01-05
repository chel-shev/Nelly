package dev.chel_shev.nelly.inquiry.bot;

import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StopHandler extends InquiryHandler<StopInquiry> {

    private final UserService userService;
    private final StopConfig stopConfig;

    @Override
    public StopInquiry executionLogic(StopInquiry inquiry) {
        userService.delete(inquiry.getUser());
        inquiry.setAnswerMessage(aSer.generateAnswer(CommandLevel.FIRST, stopConfig));
        inquiry.setKeyboardType(KeyboardType.NONE);
        return inquiry;
    }

    @Override
    public StopInquiry preparationLogic(StopInquiry stopInquiry, Message message) {
        return null;
    }
}